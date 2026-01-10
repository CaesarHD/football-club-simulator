function esc(s) {
    return String(s ?? '').replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;').replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;').replaceAll("'", '&#39;');
}

function setText(id, val) {
    const el = document.getElementById(id);
    if (el) el.textContent = val ?? '-';
}

function fmtDate(d) {
    return d ? d : '-'; // LocalDate vine ca "YYYY-MM-DD"
}

async function fetchJson(url) {
    const res = await fetch(url);
    if (!res.ok) throw new Error(`${url} -> ${res.status}`);
    return res.json();
}

(async () => {
    const role = sessionStorage.getItem('role');
    const playerName = sessionStorage.getItem('playerName');

    // guard: trebuie sa fii player
    if (role !== 'PLAYER' || !playerName) {
        // daca nu esti logat, te duce inapoi
        window.location.href = '/';
        return;
    }

    // logout
    document.getElementById('logoutBtn')?.addEventListener('click', () => {
        sessionStorage.removeItem('role');
        sessionStorage.removeItem('playerName');
        window.location.href = '/';
    });

    try {
        const p = await fetchJson(`/api/players/by-name/${encodeURIComponent(playerName)}`);

        document.title = `My Profile - ${p.name}`;
        setText('title', `My Profile - ${p.name}`);

        setText('pName', p.name);
        setText('pAge', p.age);
        setText('pSalary', p.salary ?? '-');

        const clubEl = document.getElementById('pClub');
        if (clubEl) {
            if (p.club?.name) {
                clubEl.innerHTML = `<a href="/html/matches.html?clubName=${encodeURIComponent(p.club.name)}">${esc(p.club.name)}</a>`;
            } else {
                clubEl.textContent = '-';
            }
        }

        const s = p.skills || {};
        setText('pPos', s.position ?? '-');
        setText('pPace', s.pace ?? 0);
        setText('pStamina', s.stamina ?? 0);
        setText('pDefending', s.defending ?? 0);
        setText('pPhysical', s.physical ?? 0);
        setText('pDribbling', s.dribbling ?? 0);
        setText('pShooting', s.shooting ?? 0);
        setText('pPassing', s.passing ?? 0);

        // goalkeepers only
        setText('pReflexes', s.position === 'GOALKEEPER' ? (s.reflexes ?? 0) : '-');
        setText('pDiving',   s.position === 'GOALKEEPER' ? (s.diving ?? 0) : '-');

        const hist = await fetchJson(`/api/players/history/${p.id}`);
        const body = document.getElementById('historyTable');
        body.innerHTML = '';

        if (!hist || hist.length === 0) {
            document.getElementById('historyEmpty').style.display = 'block';
            return;
        }
        document.getElementById('historyEmpty').style.display = 'none';

        hist.forEach(h => {
            const clubName = h.club?.name ?? '-';
            const tr = document.createElement('tr');
            tr.innerHTML = `
        <td><a href="/html/matches.html?clubName=${encodeURIComponent(clubName)}">${esc(clubName)}</a></td>
        <td>${esc(fmtDate(h.joinedDate))}</td>
        <td>${esc(fmtDate(h.leftDate))}</td>
        <td>${esc(h.noMatches ?? 0)}</td>
        <td>${esc(h.noGoals ?? 0)}</td>
        <td>${esc(h.noAssists ?? 0)}</td>
      `;
            body.appendChild(tr);
        });

    } catch (e) {
        console.error(e);
        setText('title', `My Profile - failed to load`);
    }
})();
