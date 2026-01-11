(() => {
    const loginBtn = document.getElementById('loginBtn');
    const loginModal = document.getElementById('loginModal');
    const closeModalBtn = document.getElementById('closeModalBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const loginForm = document.getElementById('loginForm');

    if (!loginBtn || !loginModal || !closeModalBtn || !cancelBtn || !loginForm) return;

    const usernameEl = document.getElementById('username');
    const passwordEl = document.getElementById('password');

    function openModal() {
        loginModal.style.display = 'block';
        usernameEl?.focus();
    }

    function closeModal() {
        loginModal.style.display = 'none';
        loginForm.reset();
    }

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
            // add any other truly necessary fields
            isLoggedIn: true,
            loginTime: new Date().toISOString()
        };

        // Only these keys
        sessionStorage.setItem('currentUser', JSON.stringify(fullUser));

        // Keep these if you really need them across pages
        // sessionStorage.setItem('selectedMatchId', '...');
        // sessionStorage.setItem('selectedClubId', '...');
        // sessionStorage.setItem('selectedClubName', '...');

        console.log('Clean user data saved:', fullUser);
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

        if (!username || !password) {
            alert('Please enter username and password');
            return;
        }

        try {
            console.log('Attempting login...');

            // 1. Login request
            const loginRes = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (!loginRes.ok) {
                const errorMsg = await loginRes.text().catch(() => 'Login failed');
                console.error('Login failed:', loginRes.status, errorMsg);
                alert(errorMsg);
                return;
            }

            const userInfo = await loginRes.json();
            console.log('Login successful:', userInfo);

            // 2. Load role-specific profile
            let profileData = {};

            if (userInfo.role === 'PLAYER') {
                console.log('Fetching player profile...');
                const profileRes = await fetch('/api/auth/player_profile', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ id: userInfo.id })   // ← changed to { id: ... }
                });

                if (profileRes.ok) {
                    profileData = await profileRes.json();
                    console.log('Player profile loaded:', profileData);
                } else {
                    const errorText = await profileRes.text();
                    console.error('Player profile failed:', profileRes.status, errorText);
                    alert(`Could not load player profile: ${errorText || 'Unknown error'}`);
                }
            }
            else if (userInfo.role === 'COACH') {
                console.log('Fetching coach profile...');
                const profileRes = await fetch('/api/auth/coach_profile', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ id: userInfo.id })
                });

                if (profileRes.ok) {
                    profileData = await profileRes.json();
                    console.log('Coach profile loaded:', profileData);
                } else {
                    const errorText = await profileRes.text();
                    console.error('Coach profile failed:', profileRes.status, errorText);
                }
            }
            else if (userInfo.role === 'MANAGER') {
                console.log('Fetching manager profile...');
                const profileRes = await fetch('/api/auth/manager_profile', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ id: userInfo.id })
                });

                if (profileRes.ok) {
                    profileData = await profileRes.json();
                    console.log('Manager profile loaded:', profileData);
                } else {
                    const errorText = await profileRes.text();
                    console.error('Manager profile failed:', profileRes.status, errorText);
                }
            }

            // 3. Save everything
            saveToSession(userInfo, profileData);

            console.log('Login process completed successfully');
            closeModal();

            // Optional: reload page or redirect
            // window.location.reload();

        } catch (err) {
            console.error('Login error:', err);
            alert('An error occurred during login. Check console for details.');
        }
    });
})();