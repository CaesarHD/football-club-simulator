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

        loginModal.addEventListener('click', (e) => {
            if (e.target === loginModal) closeModal();
        });

        /* ---------- LOGIN LOGIC ---------- */

        function saveToSession(userInfo, profileData) {
            const fullUser = {
                id: userInfo.id,
                username: userInfo.username,
                role: userInfo.role,
                name: profileData.name,
                age: profileData.age,
                salary: profileData.salary,
                club: profileData.club,
                skills: profileData.skills,
                isLoggedIn: true,
                loginTime: new Date().toISOString()
            };

            sessionStorage.setItem('currentUser', JSON.stringify(fullUser));
            console.log('User saved to session:', fullUser);
        }

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
                let profileData = {};

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

                    if (profileRes.ok) {
                        profileData = await profileRes.json();
                    }
                }

                saveToSession(userInfo, profileData);
                closeModal();

                switch (userInfo.role) {
                    case 'COACH':
                        window.location.href = '../html/coach_dashboard.html';
                        break;
                    case 'PLAYER':
                        window.location.href = '/html/player_dashboard.html';
                        break;
                    case 'MANAGER':
                        window.location.href = '/html/manager.html';
                        break;
                    default:
                        alert(`No redirect page defined for role: ${userInfo.role}`);
                        break;
                }

            } catch (err) {
                console.error(err);
                alert('Login failed. Check console.');
            }
        });

    });
