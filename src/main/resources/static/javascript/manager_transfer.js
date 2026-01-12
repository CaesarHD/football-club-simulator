const buyingBody = document.getElementById('buying-body');
const sellingBody = document.getElementById('selling-body');
const currentUser = JSON.parse(sessionStorage.getItem('currentUser') || '{}');

async function loadAllTransfers() {
    if (!currentUser.manager?.club?.id) {
        alert("Club session lost. Please re-login.");
        window.location.href = '../index.html';
        return;
    }

    const clubId = currentUser.manager.club.id;

    // Fetch both lists in parallel
    try {
        // FIXED: Changed /api/managers to /api/clubs to match your ClubController
        const [buyingRes, sellingRes] = await Promise.all([
            fetch(`/api/clubs/transfers/buying?clubId=${clubId}`),
            fetch(`/api/clubs/transfers/selling?clubId=${clubId}`)
        ]);

        if (!buyingRes.ok || !sellingRes.ok) {
            throw new Error("Failed to fetch transfers");
        }

        const buyingList = await buyingRes.json();
        const sellingList = await sellingRes.json();

        renderBuying(buyingList);
        renderSelling(sellingList);

    } catch (err) {
        console.error("Error loading transfers:", err);
        buyingBody.innerHTML = '<tr><td colspan="5" class="text-center text-danger">Error loading data.</td></tr>';
        sellingBody.innerHTML = '<tr><td colspan="5" class="text-center text-danger">Error loading data.</td></tr>';
    }
}

// --- RENDER BUYING (Targets) ---
function renderBuying(list) {
    buyingBody.innerHTML = '';
    // Safety check if list is null/undefined
    if (!list || list.length === 0) {
        buyingBody.innerHTML = '<tr><td colspan="5" class="text-center text-muted">No outgoing requests.</td></tr>';
        return;
    }

    list.forEach(t => {
        const tr = document.createElement('tr');

        let statusBadge = getStatusBadge(t.transferStatus);
        let inputHtml = '';
        let actionHtml = '';

        if (['PLAYER_INTERESTED', 'COACH_APPROVED', 'MANAGER_REJECTED'].includes(t.transferStatus)) {
            inputHtml = `<input type="number" id="offer-${t.id}" class="offer-input" value="${t.sum > 0 ? t.sum : ''}" placeholder="Mil €">`;
            actionHtml = `<button class="btn btn-success btn-sm" onclick="makeOffer(${t.id})">Send Offer</button>`;
        }
        else if (t.transferStatus === 'MANAGER_OFFER') {
            inputHtml = `${t.sum} Mil`;
            actionHtml = `<span class="text-warning small">Waiting for response...</span>`;
        }
        else if (t.transferStatus === 'DONE') {
            inputHtml = `${t.sum} Mil`;
            actionHtml = `<span class="text-success small">Completed</span>`;
        }

        tr.innerHTML = `
            <td>${t.player?.name || 'Unknown'}</td>
            <td>${t.player?.club?.name || 'Free Agent'}</td>
            <td>${statusBadge}</td>
            <td>${inputHtml}</td>
            <td>${actionHtml}</td>
        `;
        buyingBody.appendChild(tr);
    });
}

// --- RENDER SELLING (Offers Received) ---
function renderSelling(list) {
    sellingBody.innerHTML = '';
    if (!list || list.length === 0) {
        sellingBody.innerHTML = '<tr><td colspan="5" class="text-center text-muted">No offers received.</td></tr>';
        return;
    }

    list.forEach(t => {
        const tr = document.createElement('tr');

        let statusBadge = getStatusBadge(t.transferStatus);
        let amountDisplay = t.sum > 0 ? `${t.sum} Mil` : '-';
        let actionHtml = '';

        if (t.transferStatus === 'MANAGER_OFFER') {
            actionHtml = `
                <div class="d-flex gap-2">
                    <button class="btn btn-success btn-sm" onclick="acceptOffer(${t.id})">Accept</button>
                    <button class="btn btn-danger btn-sm" onclick="rejectOffer(${t.id})">Reject</button>
                </div>
            `;
        }
        else if (t.transferStatus === 'DONE') {
            actionHtml = `<span class="text-success small">Sold</span>`;
        }
        else if (t.transferStatus === 'MANAGER_REJECTED') {
            actionHtml = `<span class="text-muted small">You rejected this</span>`;
        }
        else {
            actionHtml = `<span class="text-muted small">No offer yet</span>`;
        }

        tr.innerHTML = `
            <td>${t.player?.name || 'Unknown'}</td>
            <td>${t.newClub?.name || 'Unknown'}</td>
            <td>${statusBadge}</td>
            <td>${amountDisplay}</td>
            <td>${actionHtml}</td>
        `;
        sellingBody.appendChild(tr);
    });
}

// --- ACTIONS ---
// Note: These still use /api/managers/... because those actions exist in your ManagerController

async function makeOffer(id) {
    const input = document.getElementById(`offer-${id}`);
    const amount = input ? input.value : 0;

    if (!amount || amount <= 0) {
        alert("Please enter a valid amount.");
        return;
    }

    try {
        const res = await fetch(`/api/managers/transfers/offer/${id}/${amount}`, { method: 'PUT' });
        if(res.ok) {
            alert("Offer sent!");
            loadAllTransfers();
        } else {
            const err = await res.json();
            alert("Error: " + (err.message || "Could not send offer"));
        }
    } catch (e) { console.error(e); }
}

async function acceptOffer(id) {
    if(!confirm("Accept offer? This will transfer the player immediately.")) return;

    try {
        const res = await fetch(`/api/managers/transfers/accept/${id}`, { method: 'PUT' });
        if(res.ok) {
            alert("Transfer Completed!");
            loadAllTransfers();
        } else {
            const err = await res.json();
            alert("Error: " + (err.message || "Failed to accept"));
        }
    } catch (e) { console.error(e); }
}

async function rejectOffer(id) {
    if(!confirm("Reject this offer?")) return;

    try {
        const res = await fetch(`/api/managers/transfers/reject/${id}`, { method: 'PUT' });
        if(res.ok) {
            loadAllTransfers();
        } else {
            alert("Error rejecting.");
        }
    } catch (e) { console.error(e); }
}

function getStatusBadge(status) {
    let cls = 'secondary';
    if(status === 'MANAGER_OFFER') cls = 'status-OFFER';
    if(status === 'DONE') cls = 'status-DONE';
    if(status === 'MANAGER_REJECTED') cls = 'status-REJECTED';
    if(status === 'PLAYER_INTERESTED' || status === 'COACH_APPROVED') cls = 'status-INTERESTED';

    return `<span class="status-badge ${cls}">${status.replace('_', ' ')}</span>`;
}

// Start
loadAllTransfers();