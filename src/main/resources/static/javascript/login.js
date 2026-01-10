(() => {
    const loginBtn = document.getElementById('loginBtn');
    const loginModal = document.getElementById('loginModal');
    const closeModalBtn = document.getElementById('closeModalBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const loginForm = document.getElementById('loginForm');

    if (!loginBtn || !loginModal || !closeModalBtn || !cancelBtn || !loginForm) return;

    function openModal() {
        loginModal.style.display = 'block';
        const u = document.getElementById('username');
        if (u) u.focus();
    }

    function closeModal() {
        loginModal.style.display = 'none';
        loginForm.reset();
    }

    loginBtn.addEventListener('click', openModal);
    closeModalBtn.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);


    loginModal.addEventListener('click', (e) => {
        if (e.target === loginModal) closeModal();
    });

    loginForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const username = document.getElementById('username')?.value ?? '';
        const password = document.getElementById('password')?.value ?? '';

        console.log('login:', { username, password });
        closeModal();
    });
})();
