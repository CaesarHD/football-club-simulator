fetch('/api/clubs')
    .then(response => {
        if (!response.ok) {
            throw new Error('Error fetching clubs: ' + response.status);
        }
        return response.json();
    })
    .then(clubs => {
        const tableBody = document.getElementById('clubs-table');
        clubs.forEach(club => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td><a href="../html/matches.html?clubName=${club.name}">${club.name}</a></td>
                <td>${club.budget} mil</td>
                <td>${club.stadium ? club.stadium.name : '-'}</td>
                <td>${club.stadium ? club.stadium.capacity : '-'}k</td>
                <td>${club.country ? club.country : '-'}</td>
            `;
            tableBody.appendChild(row);
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });