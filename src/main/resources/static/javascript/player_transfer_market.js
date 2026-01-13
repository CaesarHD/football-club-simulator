// player_transfer_market.js

// ---------- SETUP SI DATA ----------
const tableBody = document.getElementById('clubs-table-body');
const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');

// Verificăm identitatea jucătorului
const playerId = currentUser.id;
// Verificăm ID-ul clubului curent (dacă există)
const currentClubId = currentUser.profile?.club?.id || currentUser.player?.club?.id || null;

if (!playerId) {
    alert("Player not identified. Please login again.");
    window.location.href = 'index.html';
}

// ---------- LOGICA PRINCIPALA ----------

async function loadClubsAndTransfers() {
    try {
        // 1. Facem două cereri în paralel: Cluburile disponibile + Transferurile mele existente
        const [clubsResponse, transfersResponse] = await Promise.all([
            fetch('/api/clubs'),
            fetch(`/api/clubs/transfers/player/${playerId}`)
        ]);

        if (!clubsResponse.ok) throw new Error('Error fetching clubs');
        // Dacă transfersResponse dă eroare (poate player-ul nu are transferuri), continuăm cu array gol
        const clubs = await clubsResponse.json();
        const myTransfers = transfersResponse.ok ? await transfersResponse.json() : [];

        // 2. Creăm o listă cu ID-urile cluburilor unde am aplicat deja si NU e respins/gata
        // Statusuri care blochează butonul: PLAYER_INTERESTED, COACH_APPROVED, MANAGER_OFFER
        const pendingClubIds = myTransfers
            .filter(t => t.transferStatus !== 'REJECTED' && t.transferStatus !== 'DONE')
            .map(t => t.newClub.id); // Extragem ID-ul clubului tinta

        // 3. Filtram clubul curent al jucatorului (să nu apară în listă)
        const otherClubs = clubs.filter(club => String(club.id) !== String(currentClubId));

        // 4. Randam tabelul trimitand si lista de pending
        renderClubsTable(otherClubs, pendingClubIds);

    } catch (error) {
        console.error('Error loading data:', error);
        tableBody.innerHTML = '<tr><td colspan="6">Error loading market data.</td></tr>';
    }
}

function renderClubsTable(clubs, pendingClubIds) {
    tableBody.innerHTML = '';

    if (clubs.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6">No other clubs available at the moment.</td></tr>';
        return;
    }

    clubs.forEach(club => {
        const row = document.createElement('tr');

        // Nume Club
        const nameCell = document.createElement('td');
        const link = document.createElement('a');
        link.href = '../html/matches.html';
        link.textContent = club.name;
        link.onclick = (e) => {
            e.preventDefault();
            sessionStorage.setItem('selectedClubId', club.id);
            // Pentru consistenta cu fix-ul anterior din matches.js
            sessionStorage.setItem('selectedClubName', club.name);
            window.location.href = `matches.html?clubId=${club.id}`;
        };
        nameCell.appendChild(link);

        // Buget
        const budgetCell = document.createElement('td');
        budgetCell.textContent = `${club.budget} mil`;

        // Țară
        const countryCell = document.createElement('td');
        countryCell.textContent = club.country || '-';

        // Stadium
        const stadiumName = document.createElement('td');
        stadiumName.textContent = club.stadium ? club.stadium.name : '-';

        const stadiumCapacity = document.createElement('td');
        stadiumCapacity.textContent = club.stadium ? `${club.stadium.capacity}k` : '-';

        // Buton Actiune (Interest)
        const actionCell = document.createElement('td');
        const btn = document.createElement('button');

        // --- VERIFICARE PENDING ---
        // Verificăm dacă ID-ul acestui club se află în lista transferurilor mele active
        // Convertim la String pentru siguranță la comparare
        const isPending = pendingClubIds.map(String).includes(String(club.id));

        if (isPending) {
            // Dacă există deja o cerere, butonul apare direct verde și dezactivat
            updateButtonStyle(btn, true);
        } else {
            // Altfel, buton standard
            updateButtonStyle(btn, false);

            btn.addEventListener('click', () => {
                declareInterest(club.id, btn);
            });
        }

        actionCell.appendChild(btn);

        // Adaugam celulele in rand
        row.appendChild(nameCell);
        row.appendChild(budgetCell);
        row.appendChild(countryCell);
        row.appendChild(stadiumName);
        row.appendChild(stadiumCapacity);
        row.appendChild(actionCell);

        tableBody.appendChild(row);
    });
}

// ---------- HELPER FUNCTIONS ----------

function updateButtonStyle(btn, isPending) {
    if (isPending) {
        btn.className = 'btn btn-success btn-sm btn-interest disabled';
        btn.innerHTML = '&#10003; Pending'; // Schimbam textul in "Pending" sau "Request Sent"
        btn.disabled = true;
        btn.style.cursor = "not-allowed";
        btn.style.opacity = "0.7";
    } else {
        btn.className = 'btn btn-outline-light btn-sm btn-interest';
        btn.textContent = 'Declare Interest';
        btn.disabled = false;
        btn.style.cursor = "pointer";
        btn.style.opacity = "1";
    }
}

function declareInterest(clubId, btnElement) {
    const url = `/api/clubs/transfers/${playerId}/${clubId}`;

    // Punem butonul in loading state vizual
    btnElement.textContent = "Sending...";
    btnElement.disabled = true;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                updateButtonStyle(btnElement, true);
                console.log(`Success: Transfer request created for Club ID ${clubId}`);
            } else {
                // Revertim butonul daca e eroare
                updateButtonStyle(btnElement, false);
                console.error('Transfer request failed:', response.status);
                alert("Could not create transfer request.");
            }
        })
        .catch(error => {
            updateButtonStyle(btnElement, false);
            console.error('Network error:', error);
            alert("Network error occurred.");
        });
}

// Start
loadClubsAndTransfers();