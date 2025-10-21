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
                <td>${player.age}</td>
                <td>${player.position}</td>
                <td>${player.pace}</td>
                <td>${player.stamina}</td>
                <td>${player.defending}</td>
                <td>${player.physical}</td>
                <td>${player.dribbling}</td>
                <td>${player.shooting}</td>
                <td>${player.passing}</td>
            `;
            tableBody.appendChild(row);
        });
    })
    .catch(error => {

        console.error('Error:', error);
    });