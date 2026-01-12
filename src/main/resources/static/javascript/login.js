    document.addEventListener('DOMContentLoaded', () => {

        const loginBtn = document.getElementById('loginBtn');
        const loginBtn2 = document.getElementById('loginBtn2');
        const loginModal = document.getElementById('loginModal');
        const cancelBtn = document.getElementById('cancelBtn');
        const loginForm = document.getElementById('loginForm');

        if (!loginBtn || !loginModal || !cancelBtn || !loginForm) {
            console.error('Login elements missing');
            return;
        }

        const usernameEl = document.getElementById('username');
        const passwordEl = document.getElementById('password');

        /* ---------- MODAL CONTROL ---------- */

        function openModal() {
            loginModal.style.display = 'flex';
            usernameEl?.focus();
        }

        function closeModal() {
            loginModal.style.display = 'none';
            loginForm.reset();
        }

        loginBtn.addEventListener('click', openModal);
        loginBtn2?.addEventListener('click', openModal);
        cancelBtn.addEventListener('click', closeModal);
    function saveUserSession(userInfo) {
        sessionStorage.setItem('isLoggedIn', 'true');
        sessionStorage.setItem('user', JSON.stringify(userInfo));

        if (userInfo?.id != null) sessionStorage.setItem('userId', String(userInfo.id));
        if (userInfo?.username != null) sessionStorage.setItem('username', userInfo.username);
        if (userInfo?.role != null) sessionStorage.setItem('role', String(userInfo.role));
    }

    function savePlayerSession(playerInfo) {
        if (!playerInfo) return;

        if (playerInfo?.id != null) {
            sessionStorage.setItem('playerId', String(playerInfo.id));
            sessionStorage.setItem('selectedPlayerId', String(playerInfo.id));
        }
        if (playerInfo?.name != null) sessionStorage.setItem('playerName', playerInfo.name);
        if (playerInfo?.club?.name != null) sessionStorage.setItem('playerClubName', playerInfo.club.name);

        sessionStorage.setItem('playerProfile', JSON.stringify(playerInfo));
        sessionStorage.setItem('player', JSON.stringify(playerInfo)); // dacă îl folosești în alte pagini
    }

    function redirectByRole(role) {
        if (role === 'PLAYER') {
            // fiind în /index.html, asta e ok
            window.location.href = '/html/player_dashboard.html';
            return;
        }

        if (role === 'COACH') {
            window.location.href = '/html/coach_dashboard.html';
            return;
        }

        if (role === 'MANAGER') {
            window.location.href = '/html/manager_dashboard.html';
            return;
        }

        console.warn('No dashboard implemented for role:', role);
    }

    loginBtn.addEventListener('click', openModal);
    closeModalBtn.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);

        loginModal.addEventListener('click', (e) => {
            if (e.target === loginModal) closeModal();
        });

    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const username = usernameEl?.value?.trim() ?? '';
        const password = passwordEl?.value ?? '';

        try {
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

            // dacă e PLAYER, ia și profilul de player ca să umpli cheile așteptate de dashboard
            if (userInfo?.role === 'PLAYER') {
                const resPlayer = await fetch('/api/auth/player_profile', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(userInfo)
                });
                const roleEndpoints = {
                    PLAYER: '/api/auth/player_profile',
                    COACH: '/api/auth/coach_profile',
                    MANAGER: '/api/auth/manager_profile'
                };

                if (roleEndpoints[userInfo.role]) {
                    const profileRes = await fetch(roleEndpoints[userInfo.role], {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ id: userInfo.id })
                    });

                if (!resPlayer.ok) {
                    const msg = await resPlayer.text().catch(() => '');
                    console.error("couldn't load player profile:", resPlayer.status, msg);
                    // chiar dacă profilul nu vine, poți redirecționa totuși
                } else {
                    const playerInfo = await resPlayer.json();
                    savePlayerSession(playerInfo);

                    console.log('logged in user:', userInfo);
                    console.log('player profile:', playerInfo);
                }
            } else {
                console.log('logged in user:', userInfo);
            }

            closeModal();
            redirectByRole(userInfo?.role);
        } catch (err) {
            console.error("login failed:", err);
        }
    });
})();
