const stadiumNameEl = document.getElementById('stadium-name');
const stadiumCapEl = document.getElementById('stadium-cap');
const clubBudgetEl = document.getElementById('club-budget');
const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');

// 1. Initialize
async function initStadium() {
    if (!currentUser.manager?.club?.id) {
        alert("Session invalid. Please login again.");
        window.location.href = '../index.html';
        return;
    }

    // Refresh manager/club data from server to get latest budget/stadium stats
    try {
        const res = await fetch('/api/managers/manager_profile', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id: currentUser.id })
        });

        if (res.ok) {
            const manager = await res.json();
            currentUser.manager = manager;
            // Save fresh data to session
            sessionStorage.setItem('currentUser', JSON.stringify(currentUser));

            updateUI(manager.club);
        }
    } catch (e) {
        console.error("Failed to refresh profile", e);
    }
}

function updateUI(club) {
    if(!club) return;

    stadiumNameEl.textContent = club.stadium ? club.stadium.name : 'Unknown';
    // Capacity is in 'k' (thousands), convert to full number for display or keep as 'k'
    // Based on HTML <small>seats</small>, let's show full number: 50k -> 50,000
    const cap = club.stadium ? club.stadium.capacity * 1000 : 0;
    stadiumCapEl.textContent = cap.toLocaleString();

    clubBudgetEl.textContent = club.budget;
}

// 2. Expand Logic
async function expandStadium(seats) {
    const club = currentUser.manager.club;

    // Determine cost locally for quick validation
    let cost = 0;
    if (seats === 1000) cost = 1;
    if (seats === 5000) cost = 5;
    if (seats === 10000) cost = 12;

    // Client-side Budget Check
    if (club.budget < cost) {
        alert(`Insufficient funds! You need ${cost} million, but only have ${club.budget}.`);
        return;
    }

    if (!confirm(`Confirm expansion of ${seats.toLocaleString()} seats for €${cost} Million?`)) {
        return;
    }

    try {
        const response = await fetch('/api/clubs/stadium/expand', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                clubId: club.id,
                seats: seats
            })
        });

        if (response.ok) {
            alert("Stadium expansion successful!");
            // Reload data to reflect new budget and capacity
            initStadium();
        } else {
            const err = await response.json(); // If backend sends error JSON
            alert("Expansion failed: " + (err.message || response.statusText));
        }
    } catch (err) {
        console.error(err);
        alert("Network error.");
    }
}

// Start
initStadium();