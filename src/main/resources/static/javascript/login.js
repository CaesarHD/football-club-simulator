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

    function saveToSession(userInfo) {
        sessionStorage.setItem('isLoggedIn', 'true');
        sessionStorage.setItem('user', JSON.stringify(userInfo));

        if (userInfo?.id != null) sessionStorage.setItem('userId', String(userInfo.id));
        if (userInfo?.username != null) sessionStorage.setItem('username', userInfo.username);
        if (userInfo?.role != null) sessionStorage.setItem('role', String(userInfo.role));
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
                console.error('couldn\'t authenticate:', res.status, msg);
                return;
            }

            const userInfo = await res.json();
            console.log(userInfo);


            const resPlayer = await fetch('api/auth/player_profile', {
               method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userInfo)
            });

            if (!resPlayer.ok) {
                const msg = await res.text().catch(() => '');
                console.error('couldn\'t authenticate:', res.status, msg);
                return;
            }
            const playerInfo = await resPlayer.json();
            saveToSession(playerInfo);

            console.log('logged in:', playerInfo);
            closeModal();
        } catch (err) {
            console.error("couldn't authenticate", err);
        }
    });
})();
