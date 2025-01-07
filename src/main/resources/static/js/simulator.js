function toggleCharacterImage() {
    const popup = document.getElementById('characterPopup');
    popup.style.display = popup.style.display === 'flex' ? 'none' : 'flex';
}
function toggleLightConeImage() {
    const popup = document.getElementById('lightConePopup');
    popup.style.display = popup.style.display === 'flex' ? 'none' : 'flex';
}

function selectCharacter(selectedImage) {
    const buttonImage = document.getElementById('character-image');
    buttonImage.src = selectedImage;

    document.getElementById('characterPopup').style.display = 'none';
}
function selectLightCone(selectedImage) {
    const buttonImage = document.getElementById('lightCone-image');
    buttonImage.src = selectedImage;

    document.getElementById('lightConePopup').style.display = 'none';
}

function loadUserInfo() {
    // AJAX 요청
    fetch('/simulator/userinfo')  // 서버에 요청 보낼 URL
        .then(response => response.json())
        .then(data => {
            // 데이터를 받아온 후 DOM 업데이트
            document.getElementById('characterCount').textContent = data.characterCount;
            document.getElementById('characterIsFull').textContent = data.characterIsFull ? 'O' : 'X';
            document.getElementById('lightConeCount').textContent = data.lightConeCount;
            document.getElementById('lightConeIsFull').textContent = data.lightConeIsFull ? 'O' : 'X';
        })
        .catch(error => console.error('Error loading user info:', error));
}
function clearCount() {
    fetch('/simulator/clearCount')  // 서버에 요청 보낼 URL
        .then(response => response.json())
        .then(data => {
            // 데이터를 받아온 후 DOM 업데이트
            document.getElementById('characterCount').textContent = data.characterCount;
            document.getElementById('characterIsFull').textContent = data.characterIsFull ? 'O' : 'X';
            document.getElementById('lightConeCount').textContent = data.lightConeCount;
            document.getElementById('lightConeIsFull').textContent = data.lightConeIsFull ? 'O' : 'X';
        })
        .catch(error => console.error('Error loading user info:', error));
}

function simulateCharacter() {
    // 현재 화면 값 가져오기
    const characterCount = parseInt(document.getElementById("characterCount").textContent);
    const characterIsFull = document.getElementById("characterIsFull").textContent === "O";
    const characterImageSrc = document.getElementById("character-image").src;
    const imageName = characterImageSrc.substring(characterImageSrc.lastIndexOf('/') + 1);

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // 요청 데이터 생성
    const requestData = {
        characterCount: characterCount,
        characterIsFull: characterIsFull,
        imageName: imageName
    };

    // AJAX 요청 보내기
    fetch('/simulator/simulateCharacter', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json()
            .then(data => {
                document.getElementById("characterCount").textContent = data.characterCount;
                document.getElementById("characterIsFull").textContent = data.characterIsFull ? "O" : "X";
                const items = data.items;
                const gridItems = document.querySelectorAll('.grid-item-character');

                if (items && gridItems.length === items.length) {
                    let delay = 0; // 딜레이 시간 초기화

                    for (let i = 0; i < items.length; i++) {
                        const item = items[i];
                        const gridItem = gridItems[i];

                        if (item && item.imagePath) {
                            setTimeout(() => { // setTimeout으로 딜레이 적용
                                gridItem.innerHTML = '';

                                const img = document.createElement('img');
                                img.src = item.imagePath;
                                img.alt = `Item ${i + 1}`;
                                img.style.maxWidth = '100%';
                                img.style.maxHeight = '100%';
                                img.style.objectFit = 'cover';

                                switch (item.star) {
                                    case "FIVE":
                                        img.classList.add('five-star-effect');
                                        break;
                                    case "FOUR":
                                        img.classList.add('four-star-effect');
                                        break;
                                    case "THREE":
                                        img.classList.add('three-star-effect');
                                        break;
                                    default:
                                        break;
                                }

                                gridItem.appendChild(img);
                                gridItem.style.opacity = 1; // 보이도록 설정
                            }, delay);

                            delay += 100; // 딜레이 0.1초(100ms)씩 증가
                        } else {
                            gridItem.textContent = "이미지 없음";
                            console.error(`Item ${i + 1}에 imagePath가 없습니다.`);
                        }
                    }
                } else {
                    console.error("데이터 또는 grid-item 개수가 일치하지 않습니다.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('시뮬레이션 중 오류가 발생했습니다.');
            }))
}

function simulateLightCone() {
    // 현재 화면 값 가져오기
    const lightConeCount = parseInt(document.getElementById("lightConeCount").textContent);
    const lightConeIsFull = document.getElementById("lightConeIsFull").textContent === "O";
    const lightConeImageSrc = document.getElementById("lightCone-image").src;
    const imageName = lightConeImageSrc.substring(lightConeImageSrc.lastIndexOf('/') + 1);

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // 요청 데이터 생성
    const requestData = {
        lightConeCount: lightConeCount,
        lightConeIsFull: lightConeIsFull,
        imageName: imageName
    };

    // AJAX 요청 보내기
    fetch('/simulator/simulateLightCone', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json()
            .then(data => {
                document.getElementById("lightConeCount").textContent = data.lightConeCount;
                document.getElementById("lightConeIsFull").textContent = data.lightConeIsFull ? "O" : "X";
                const items = data.items;
                const gridItems = document.querySelectorAll('.grid-item-lightCone');

                if (items && gridItems.length === items.length) {
                    let delay = 0; // 딜레이 시간 초기화

                    for (let i = 0; i < items.length; i++) {
                        const item = items[i];
                        const gridItem = gridItems[i];

                        if (item && item.imagePath) {
                            setTimeout(() => { // setTimeout으로 딜레이 적용
                                gridItem.innerHTML = '';

                                const img = document.createElement('img');
                                img.src = item.imagePath;
                                img.alt = `Item ${i + 1}`;
                                img.style.maxWidth = '100%';
                                img.style.maxHeight = '100%';
                                img.style.objectFit = 'cover';

                                switch (item.star) {
                                    case "FIVE":
                                        img.classList.add('five-star-effect');
                                        break;
                                    case "FOUR":
                                        img.classList.add('four-star-effect');
                                        break;
                                    case "THREE":
                                        img.classList.add('three-star-effect');
                                        break;
                                    default:
                                        break;
                                }

                                gridItem.appendChild(img);
                                gridItem.style.opacity = 1; // 보이도록 설정
                            }, delay);

                            delay += 100; // 딜레이 0.1초(100ms)씩 증가
                        } else {
                            gridItem.textContent = "이미지 없음";
                            console.error(`Item ${i + 1}에 imagePath가 없습니다.`);
                        }
                    }
                } else {
                    console.error("데이터 또는 grid-item 개수가 일치하지 않습니다.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('시뮬레이션 중 오류가 발생했습니다.');
            }))
}