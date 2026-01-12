document.addEventListener('DOMContentLoaded', async () => {
    const userData = JSON.parse(sessionStorage.getItem('currentUser') || '{}');

    // Auth guard
    if (!userData || !userData.isLoggedIn || userData.role !== 'COACH') {
        window.location.href = '/';
        return;
    }

    // Welcome message
    const welcomeElement = document.getElementById('welcomeMessage');
    welcomeElement.textContent = `Welcome, Coach ${userData.profile?.name || userData.username}!`;

    // Club info
    const club = userData.profile?.club;
    const clubName = club?.name || 'Liverpool';
    const clubId = club?.id;

    // Club logo (top)
    const clubLogo = document.getElementById('clubLogo');
    const logoPath = `/assets/images/${clubName}.png`;
    clubLogo.src = logoPath;
    clubLogo.onerror = () => {
        clubLogo.src = '/assets/images/Liverpool.png';
    };

    // Logout
    document.getElementById('logoutBtn').addEventListener('click', () => {
        sessionStorage.removeItem('currentUser');
        window.location.href = '/';
    });

    /* ===============================
       TEAM MANAGEMENT CARD
    =============================== */
    const teamCard = document.getElementById('teamManagementCard');
    teamCard.addEventListener('click', async () => {
        if (!clubId) {
            alert('Your club is not assigned yet.');
            return;
        }

        try {
            const res = await fetch(`/api/clubs/next_match?clubId=${clubId}`);
            if (!res.ok) throw new Error('Failed to fetch next match');

            const nextMatch = await res.json();

            if (nextMatch && nextMatch.id) {
                sessionStorage.setItem('selectedMatchId', nextMatch.id);
            }

            sessionStorage.setItem('selectedClubId', clubId);
            sessionStorage.setItem('selectedClubName', clubName);

            window.location.href = '../html/match_stats.html';
        } catch (err) {
            console.error(err);
            alert('Could not load next match.');
        }
    });

    const transfersCard = document.getElementById('transfersCard');

    if (transfersCard) {
        transfersCard.addEventListener('click', () => {
            if (!clubId) {
                alert('Your club is not assigned yet.');
                return;
            }

            sessionStorage.setItem('selectedClubId', clubId);
            sessionStorage.setItem('selectedClubName', clubName);

            window.location.href = '../html/transfers.html';
        });
    }

    /* ===============================
       MATCHES CARD (DISPLAY NEXT MATCH)
    =============================== */
    const matchesCard = document.getElementById('matchesCard');
    const nextMatchInfo = document.getElementById('nextMatchInfo');

    matchesCard.addEventListener('click', () => {
        sessionStorage.setItem('selectedClubId', clubId);
        sessionStorage.setItem('selectedClubName', clubName);
        window.location.href = '../html/matches.html';
    });

    if (!clubId) {
        nextMatchInfo.innerHTML = `<p>Club not assigned.</p>`;
        return;
    }

    try {
        const res = await fetch(`/api/clubs/next_match?clubId=${clubId}`);
        if (!res.ok) throw new Error('Failed to fetch next match');

        const nextMatch = await res.json();

        if (!nextMatch || !nextMatch.id) {
            nextMatchInfo.innerHTML = `<p>No upcoming matches.</p>`;
            return;
        }

        const home = nextMatch.homeClub.name;
        const away = nextMatch.awayClub.name;
        const matchDate = new Date(nextMatch.date);

        nextMatchInfo.innerHTML = `
            <div style="display:flex; align-items:center; gap:10px;">
                <img src="/assets/images/${home}.png"
                     onerror="this.src='/assets/images/default.png'"
                     style="width:28px;">
                <strong>${home}</strong>

                <span>vs</span>

                <img src="/assets/images/${away}.png"
                     onerror="this.src='/assets/images/default.png'"
                     style="width:28px;">
                <strong>${away}</strong>
            </div>
            <p style="margin-top:8px;">
                🗓️ ${matchDate.toLocaleDateString()} — 
                ${matchDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
            </p>
        `;
    } catch (err) {
        console.error('Error loading next match:', err);
        nextMatchInfo.innerHTML = `<p>Unable to load next match.</p>`;
    }
});
