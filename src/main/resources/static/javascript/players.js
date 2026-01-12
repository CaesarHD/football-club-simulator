fetch('/api/players')
    .then(response => {
        if (!response.ok) {
            throw new Error('Error fetching players: ' + response.status);
        }
        return response.json();
    })
    .then(players => {
        const tableBody = document.getElementById('players-table');

        players.forEach(player => {
            const skills = player.skills || {};

            const row = document.createElement('tr');

            // Fill the row cells
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

            // Make the entire row clickable
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

