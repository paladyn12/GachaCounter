function redirectToCalculator() {
    window.location.href = "/calculator";
}
function redirectToChecklist() {
    window.location.href = "/checklist";
}
function redirectToSimulator() {
    window.location.href = "/simulator";
}


function redirectToGoogle() {
    window.location.href = "/oauth2/authorization/google";
}

function showPopup() {
    const popup = document.createElement('div');
    popup.style.position = 'fixed';
    popup.style.top = '50%';
    popup.style.left = '50%';
    popup.style.transform = 'translate(-50%, -50%)';
    popup.style.backgroundColor = '#fff';
    popup.style.padding = '20px';
    popup.style.boxShadow = '0 4px 8px rgba(0,0,0,0.2)';
    popup.style.borderRadius = '8px';
    popup.innerHTML = `
    <form id="popupForm" onsubmit="submitPopup(event)">
        <div style="display: flex; align-items: start; gap: 20px;">
            <div>
                <p>캐릭터 스택</p>
                <input type="number" id="characterCount" required>
                <p>광추 스택</p>
                <input type="number" id="lightConeCount" required>
            </div>
            <div style="margin-top: 4px;">
                <div style="display: flex; align-items: center; gap: 5px;">
                    <input type="checkbox" id="characterIsFull">
                    <label for="characterIsFull">천장</label>
                </div>
                <div style="display: flex; align-items: center; gap: 5px; margin-top: 45px;">
                    <input type="checkbox" id="lightConeIsFull">
                    <label for="lightConeIsFull">천장</label>
                </div>
            </div>
        </div>
        <div style="margin-top: 20px;">
            <button type="submit">저장</button>
            <button type="button" onclick="closePopup()">취소</button>
        </div>
    </form>
`;

    document.body.appendChild(popup);
}

function closePopup() {
    const popup = document.querySelector('body > div:last-child');
    if (popup) popup.remove();
}

function submitPopup(event) {
    event.preventDefault();

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const characterCount = document.getElementById('characterCount').value;
    const lightConeCount = document.getElementById('lightConeCount').value;
    document.getElementById('characterIsFull').value = false;
    document.getElementById('lightConeIsFull').value = false;

    const characterIsFull = document.getElementById('characterIsFull').checked;
    const lightConeIsFull = document.getElementById('lightConeIsFull').checked;

    fetch('/counter/set', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({
            characterCount,
            lightConeCount,
            characterIsFull,
            lightConeIsFull
        }),
        redirect: 'follow'  // redirect 응답을 따라가도록 설정
    })
        .then(response => {
            if (response.redirected) {
                // redirect된 URL로 이동
                window.location.href = response.url;
            } else if (response.ok) {
                location.reload();
            } else {
                response.text().then(text => {
                    console.error('Server Error:', response.status, text);
                    alert(`설정 중 오류가 발생했습니다. (상태 코드: ${response.status})`);
                });
            }
        })
        .catch(error => {
            console.error('Fetch Error:', error);
            alert('설정 중 오류가 발생했습니다.');
        });
}