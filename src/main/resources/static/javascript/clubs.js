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

            // Create link for the club name
            const link = document.createElement('a');
            link.href = '../html/matches.html'; // navigate to matches page
            link.textContent = club.name;

            // Save only the club ID in sessionStorage on click
            link.addEventListener('click', function(event) {
                event.preventDefault(); // stop default navigation
                sessionStorage.setItem('selectedClubId', club.id); // save only ID
                window.location.href = this.href; // navigate manually
            });

            // Build the row
            row.innerHTML = `
                <td></td>
                <td>${club.budget} mil</td>
                <td>${club.stadium ? club.stadium.name : '-'}</td>
                <td>${club.stadium ? club.stadium.capacity : '-'}k</td>
                <td>${club.country ? club.country : '-'}</td>
            `;

            row.querySelector('td').appendChild(link); // insert link in first cell
            tableBody.appendChild(row);
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });
