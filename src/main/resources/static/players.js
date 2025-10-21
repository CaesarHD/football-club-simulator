fetch('/players')
    .then(response => {
        if (!response.ok) {
            throw new Error('Error fetching players: ' + response.status);
        }
        return response.json();
    })
    .then(players => {
        const tableBody = document.getElementById('players-table');
        players.forEach(player => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${player.id}</td>
                <td>${player.name}</td>
            `;
            tableBody.appendChild(row);
        });
    })
    .catch(error => {

        console.error('Error:', error);
    });