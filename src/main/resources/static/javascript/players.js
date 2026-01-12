document.addEventListener("DOMContentLoaded", () => {
    const tableBody = document.getElementById('players-table');

    // 1. Check if we have a clubId in the URL (Manager view)
    const urlParams = new URLSearchParams(window.location.search);
    const clubIdParam = urlParams.get('clubId');

    // 2. Fetch data
    fetch('/api/players')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching players: ' + response.status);
            }
            return response.json();
        })
        .then(allPlayers => {
            // 3. Filter Logic
            let playersToDisplay = allPlayers;

            if (clubIdParam) {
                // Filter: Show only players where club.id matches the URL param
                playersToDisplay = allPlayers.filter(p => p.club && String(p.club.id) === String(clubIdParam));

                // Optional: Update title if you have an h1
                const titleEl = document.querySelector('h1');
                if(titleEl && playersToDisplay.length > 0) {
                    titleEl.textContent = `${playersToDisplay[0].club.name} Squad`;
                }
            }

            // 4. Clear table before rendering (good practice)
            tableBody.innerHTML = '';

            if (playersToDisplay.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="13" class="text-center">No players found.</td></tr>';
                return;
            }

            // 5. Render (Your original logic preserved exactly)
            playersToDisplay.forEach(player => {
                const skills = player.skills || {};

                const row = document.createElement('tr');

                // Fill the row cells (Your original layout)
                row.innerHTML = `
                    <td>${player.name}</td>
                    <td>${player.age}</td>
                    <td>${player.club ? player.club.name : 'No club'}</td>
                    <td>${skills.position || '-'}</td>
                    <td>${skills.pace ?? '-'}</td>
                    <td>${skills.stamina ?? '-'}</td>
                    <td>${skills.defending ?? '-'}</td>
                    <td>${skills.physical ?? '-'}</td>
                    <td>${skills.dribbling ?? '-'}</td>
                    <td>${skills.shooting ?? '-'}</td>
                    <td>${skills.passing ?? '-'}</td>
                    <td>${skills.position === 'GOALKEEPER' ? (skills.reflexes ?? '-') : '-'}</td>
                    <td>${skills.position === 'GOALKEEPER' ? (skills.diving ?? '-') : '-'}</td>
                `;

                // Make the entire row clickable (Your original logic)
                row.style.cursor = 'pointer';
                row.addEventListener('click', () => {
                    sessionStorage.setItem('selectedPlayerId', player.id);
                    window.location.href = 'players_history.html';
                });

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
});