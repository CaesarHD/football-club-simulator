document.addEventListener('DOMContentLoaded', () => {
    const loginBtn = document.getElementById('loginBtn');
    const loginBtn2 = document.getElementById('loginBtn2');
    const loginModal = document.getElementById('loginModal');
    const cancelBtn = document.getElementById('cancelBtn');
    const loginForm = document.getElementById('loginForm');
    const usernameEl = document.getElementById('username');
    const passwordEl = document.getElementById('password');
    const closeModalBtn = document.getElementById('closeModalBtn');

    if (!loginBtn || !loginModal || !cancelBtn || !loginForm) {
        console.error('Login elements missing');
        return;
    }

    // ---------- MODAL ----------
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
    closeModalBtn?.addEventListener('click', closeModal);
    loginModal.addEventListener('click', e => { if (e.target === loginModal) closeModal(); });

    // ---------- SESSION ----------
    function saveUserSession(userInfo, profileData, playerData = null) {
        const sessionData = {
            id: userInfo.id,
            username: userInfo.username,
            role: userInfo.role,
            profile: profileData,
            player: playerData, // only for coaches/managers who may select a player
            isLoggedIn: true,
            loginTime: new Date().toISOString()
        };
        sessionStorage.setItem('currentUser', JSON.stringify(sessionData));
        console.log('Saved to sessionStorage:', sessionData);
    }

    // ---------- LOGIN ----------
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const username = usernameEl.value.trim();
        const password = passwordEl.value;

        if (!username || !password) {
            alert('Please enter username and password');
            return;
        }

        try {
            const loginRes = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (!loginRes.ok) {
                alert(await loginRes.text());
                return;
            }

            const userInfo = await loginRes.json();
            const roleEndpoints = {
                PLAYER: '/api/auth/player_profile',
                COACH: '/api/auth/coach_profile',
                MANAGER: '/api/auth/manager_profile'
            };

            let profileData = {};
            let playerData = null;

            // Fetch role-specific profile
            const endpoint = roleEndpoints[userInfo.role];
            if (endpoint) {
                const profileRes = await fetch(endpoint, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ id: userInfo.id })
                });
                if (profileRes.ok) {
                    profileData = await profileRes.json();
                }
            }

            // For COACH/Manager, optionally fetch a selected player (if your app needs it)
            if (userInfo.role === 'COACH' && profileData.selectedPlayerId) {
                const playerRes = await fetch(`/api/players/${profileData.selectedPlayerId}`);
                if (playerRes.ok) playerData = await playerRes.json();
            }

            saveUserSession(userInfo, profileData, playerData);
            closeModal();

            // Redirect by role
            switch (userInfo.role) {
                case 'PLAYER':
                    window.location.href = '/html/player_dashboard.html';
                    break;
                case 'COACH':
                    window.location.href = '/html/coach_dashboard.html';
                    break;
                case 'MANAGER':
                    window.location.href = '/html/manager_dashboard.html';
                    break;
                default:
                    alert(`No dashboard configured for role: ${userInfo.role}`);
            }

        } catch (err) {
            console.error('Login failed:', err);
            alert('Login failed. Check console.');
        }
    });
});
