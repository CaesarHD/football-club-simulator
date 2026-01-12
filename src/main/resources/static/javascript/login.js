(() => {
    document.addEventListener('DOMContentLoaded', () => {
        const loginBtn = document.getElementById('loginBtn');
        const loginBtn2 = document.getElementById('loginBtn2');
        const loginModal = document.getElementById('loginModal');
        const cancelBtn = document.getElementById('cancelBtn');
        const loginForm = document.getElementById('loginForm');

        const usernameEl = document.getElementById('username');
        const passwordEl = document.getElementById('password');

        // optional (if you have an X button)
        const closeModalBtn = document.getElementById('closeModalBtn');

        if (!loginBtn || !loginModal || !cancelBtn || !loginForm) {
            console.error('Login elements missing');
            return;
        }

        /* ---------- MODAL CONTROL ---------- */
        function openModal() {
            loginModal.style.display = 'flex';
            if (usernameEl) usernameEl.focus();
        }

        function closeModal() {
            loginModal.style.display = 'none';
            loginForm.reset();
        }

        loginBtn.addEventListener('click', openModal);
        if (loginBtn2) loginBtn2.addEventListener('click', openModal);
        cancelBtn.addEventListener('click', closeModal);
        if (closeModalBtn) closeModalBtn.addEventListener('click', closeModal);

        loginModal.addEventListener('click', (e) => {
            if (e.target === loginModal) closeModal();
        });

        /* ---------- SESSION ---------- */
        function saveUserSession(userInfo) {
            sessionStorage.setItem('isLoggedIn', 'true');
            sessionStorage.setItem('user', JSON.stringify(userInfo ?? {}));

            if (userInfo?.id != null) sessionStorage.setItem('userId', String(userInfo.id));
            if (userInfo?.username != null) sessionStorage.setItem('username', String(userInfo.username));
            if (userInfo?.role != null) sessionStorage.setItem('role', String(userInfo.role));
        }

        function savePlayerSession(playerInfo) {
            if (!playerInfo) return;

            if (playerInfo?.id != null) {
                sessionStorage.setItem('playerId', String(playerInfo.id));
                sessionStorage.setItem('selectedPlayerId', String(playerInfo.id));
            }
            if (playerInfo?.name != null) sessionStorage.setItem('playerName', String(playerInfo.name));
            if (playerInfo?.club?.name != null) sessionStorage.setItem('playerClubName', String(playerInfo.club.name));

            sessionStorage.setItem('playerProfile', JSON.stringify(playerInfo));
            sessionStorage.setItem('player', JSON.stringify(playerInfo));
        }

        function redirectByRole(role) {
            if (role === 'PLAYER') return (window.location.href = '/html/player_dashboard.html');
            if (role === 'COACH') return (window.location.href = '/html/coach_dashboard.html');
            if (role === 'MANAGER') return (window.location.href = '/html/manager_dashboard.html');

            console.warn('No dashboard implemented for role:', role);
        }

        /* ---------- LOGIN SUBMIT ---------- */
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const username = usernameEl?.value?.trim() ?? '';
            const password = passwordEl?.value ?? '';

            if (!username || !password) {
                console.error('Username or password missing');
                return;
            }

            try {
                // 1) login
                const res = await fetch('/api/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username, password })
                });

                if (!res.ok) {
                    const msg = await res.text().catch(() => '');
                    console.error("couldn't authenticate:", res.status, msg);
                    return;
                }

                const userInfo = await res.json();
                saveUserSession(userInfo);

                // 2) load profile by role (optional but useful)
                const roleEndpoints = {
                    PLAYER: '/api/auth/player_profile',
                    COACH: '/api/auth/coach_profile',
                    MANAGER: '/api/auth/manager_profile'
                };

                const endpoint = roleEndpoints[userInfo?.role];

                if (endpoint) {
                    const profileRes = await fetch(endpoint, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ id: userInfo.id })
                    });

                    if (!profileRes.ok) {
                        const msg = await profileRes.text().catch(() => '');
                        console.error("couldn't load profile:", profileRes.status, msg);
                    } else {
                        const profileInfo = await profileRes.json();

                        if (userInfo.role === 'PLAYER') {
                            savePlayerSession(profileInfo);
                            console.log('player profile:', profileInfo);
                        } else {
                            // optional generic save for coach/manager
                            sessionStorage.setItem('roleProfile', JSON.stringify(profileInfo));
                            console.log('role profile:', profileInfo);
                        }
                    }
                }

                console.log('logged in user:', userInfo);

                closeModal();
                redirectByRole(userInfo?.role);
            } catch (err) {
                console.error('login failed:', err);
            }
        });
    });
})();
