fetch('/clubs')
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
                <td>${club.name}</td>
                <td>${club.budget} mil</td>
            `;
            tableBody.appendChild(row);
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });