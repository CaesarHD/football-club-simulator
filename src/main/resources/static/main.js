let player = {
    name: name
}

function displayPlayers() {
    fetch("http://localhost:8080/api/players")
        .then(r => r.json())
        .then(players => {
            const list = document.getElementById("players");
            players.forEach(p => {
                const li = document.createElement("li");
                li.textContent = p.name;
                list.appendChild(li);
            });
        });
}

displayPlayers();