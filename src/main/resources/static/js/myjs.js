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
    const characterImageSrc = document.getElementById("character-image").src;
    if (characterImageSrc.endsWith('/images/character/character_thumbnail.png')) {
        alert('캐릭터를 선택해주세요.');
        return;
    }
    // 현재 화면 값 가져오기
    const characterCount = parseInt(document.getElementById("characterCount").textContent);
    const characterIsFull = document.getElementById("characterIsFull").textContent === "O";
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
    const lightConeImageSrc = document.getElementById("lightCone-image").src;
    if (lightConeImageSrc.endsWith('/images/lightCone/lightCone_thumbnail.png')) {
        alert('광추를 선택해주세요.');
        return;
    }
    // 현재 화면 값 가져오기
    const lightConeCount = parseInt(document.getElementById("lightConeCount").textContent);
    const lightConeIsFull = document.getElementById("lightConeIsFull").textContent === "O";
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
function showHelp() {
    document.getElementById('helpPopup').style.display = 'block';
}

// 도움말 팝업 닫기
function closeHelp() {
    document.getElementById('helpPopup').style.display = 'none';
}

// ESC 키로 팝업 닫기
window.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeHelp();
    }
});

// 팝업 외부 클릭시 닫기
window.addEventListener('click', function(event) {
    const popup = document.getElementById('helpPopup');
    if (event.target === popup) {
        closeHelp();
    }
});
function goBack() {
    window.location.href = '/';
}

function toggleMenu() {
    const menuPopup = document.getElementById('menuPopup');
    menuPopup.style.display = menuPopup.style.display === 'block' ? 'none' : 'block';
}

function navigateTo(path) {
    window.location.href = path;
}

// 메뉴 외부 클릭시 닫기
document.addEventListener('click', function(event) {
    const menuPopup = document.getElementById('menuPopup');
    const menuButton = document.querySelector('.nav-button:nth-child(2)');

    if (!menuPopup.contains(event.target) && event.target !== menuButton) {
        menuPopup.style.display = 'none';
    }
});
function showContent(categoryId) {
    // 모든 버튼에서 active 클래스 제거
    document.querySelectorAll('.category-button').forEach(button => {
        button.classList.remove('active');
    });

    // 클릭된 카테고리의 버튼에 active 클래스 추가
    event.target.classList.add('active');

    // 모든 컨텐츠 숨기기
    document.querySelectorAll('.content-section').forEach(section => {
        section.classList.remove('active');
    });

    // 선택된 카테고리의 컨텐츠 표시
    document.getElementById(categoryId).classList.add('active');
}

const descriptions = {
    chest: {
        description: "각 맵에 숨겨진 상자들을 찾아 성옥 획득 가능\n" +
            "일반 : 5 성옥, 풍부한 : 5 성옥, 귀중한 : 30성옥, 통 or 저금통 : 20 성옥\n" +
            "지도 사이트 : https://star-rail-map.appsample.com"
    },
    credit: {
        description: "야릴로, 나부, 페나코니에서 획득 가능한 실드, 순촉, 금시계 크레딧을 각 지역에서 성옥 교환\n" +
            "페나코니 지역의 종이새를 찾아 성옥 획득 가능\n" +
            "실드 : 총 300 성옥 https://flatsun.tistory.com/3809\n" +
            "순촉 : 총 600 성옥 https://flatsun.tistory.com/3823\n" +
            "금시계 크레딧 : 총 800 성옥 https://flatsun2.com/27494\n" +
            "종이새 : 총 330 성옥 (지도 사이트에서 확인 가능)"
    },
    simulated: {
        description: "시뮬레이션 우주 첫 클리어 (각 세계 별 300 성옥) 및 도감작 보상 (축복, 기물, 사건 별 30 성옥)\n" +
            "추가로 각 컨텐츠 별 컨텐츠 보상 존재\n" +
            "곤충 떼 재난 : 4500 성옥 https://www.hoyolab.com/article/21450152\n" +
            "황금과 기계 : 4000 성옥 https://www.hoyolab.com/article/24017422\n" +
            "인지 불가 영역 : 3500성옥 https://www.hoyolab.com/article/34558366"
    },
    differential: {
        description: "차분화 우주 적합 레벨 및 도감작 보상 으로 3000 성옥 이상 획득\n" +
            "공략글 : https://www.hoyolab.com/article/30102658"
    },
    grizzly: {
        description: "망각의 정원 야릴로 지역 1-15단계 클리어 보상 3000 성옥"
    },
    nabu: {
        description: "망각의 정원 나부 지역 1-6단계 클리어 보상 1200 성옥"
    },
    levelup: {
        description: "특정 레벨 달성 시 100 성옥"
    },
    quest: {
        description: "메인 및 서브 퀘스트 클리어를 통해 최대 60 성옥"
    },
    achievement: {
        description: "업적별로 5-20 성옥"
    },
    message: {
        description: "캐릭터로부터 온 메세지 수신 시 5 성옥"
    },
    visit: {
        description: "열차에 방문한 캐릭터와 대화 시 10 성옥"
    },
    tutorial: {
        description: "튜토리얼 각 목록마다 1 성옥"
    },
    event: {
        description: "버전 별 이벤트 참여\n" +
            "픽업 캐릭터 체험 20 성옥\n" +
            "무명의 공훈 패스 구매 시 성옥, 일정 레벨마다 전용티켓 획득"
    },
    gift: {
        description: "사용 가능한 리딤 코드 입력 시 최대 300 성옥\n" +
            "리딤 코드 모음 사이트 : https://arca.live/b/hkstarrail/72618649"
    },
    exchange: {
        description: "매월 1일 초기화되는 잔화 교환 티켓 구매\n" +
            "상점 - 잔화교환 - 별의 궤도 전용티켓 * 5, 별의 궤도 티켓 * 5"
    },
    chaos: {
        description: "격주로 초기화되는 컨텐츠\n" +
            "컨텐츠 별 올 클리어 시 800 성옥"
    },
    weekly: {
        description: "매주 시뮬레이션 우주 점수 보상 225 성옥"
    },
    daily: {
        description: "매일 활약도 500 달성 시 60 성옥"
    },
    pass: {
        description: "열차 보급 허가증 구매 시 300 성옥 + 매일 90 성옥"
    }
};

function showDescription(id) {
    const popup = document.getElementById('descriptionPopup');
    const content = descriptions[id];

    const description = content.description.replace(
        /(https?:\/\/[^\s]+)/g,
        '<a href="$1" target="_blank">$1</a>'
    );
    document.getElementById('popupDescription').innerHTML = description;

    popup.style.display = 'flex';
}

function closeDescription() {
    document.getElementById('descriptionPopup').style.display = 'none';
}

let targetCount = 0;

function addTarget() {
    targetCount++;
    const targetsDiv = document.getElementById('targets');
    const targetItem = document.createElement('div');
    targetItem.className = 'target-item';
    targetItem.id = `target-${targetCount}`;

    targetItem.innerHTML = `
        <div>
            <label>캐릭터:</label>
            <select id="char-${targetCount}">
                <option value="X">X</option>
                <option value="명함">명함</option>
                <option value="1돌">1돌</option>
                <option value="2돌">2돌</option>
                <option value="3돌">3돌</option>
                <option value="4돌">4돌</option>
                <option value="5돌">5돌</option>
                <option value="6돌">6돌</option>
            </select>
        </div>
        <div>
            <label>광추:</label>
            <select id="lightcone-${targetCount}">
                <option value="X">X</option>
                <option value="명함">명함</option>
                <option value="1재">1재</option>
                <option value="2재">2재</option>
                <option value="3재">3재</option>
                <option value="4재">4재</option>
                <option value="5재">5재</option>
            </select>
        </div>
        <button class="remove-btn" onclick="removeTarget(${targetCount})">-</button>
        <button class="add-btn" onclick="addTarget()">+</button>
    `;
    targetsDiv.appendChild(targetItem);
}

function removeTarget(id) {
    const targetItem = document.getElementById(`target-${id}`);
    targetItem.remove();
}

// 초기 목표 입력 버튼 추가
window.onload = function() {
    addTarget();
}

function calculate() {
    const sungok = parseInt(document.getElementById('sungok').value);
    const ticket = parseInt(document.getElementById('ticket').value);
    const characterCount = parseInt(document.getElementById('characterCount').textContent);
    const characterIsFull = document.getElementById("characterIsFull").textContent === "O";
    const lightConeCount = parseInt(document.getElementById('lightConeCount').textContent);
    const lightConeIsFull = document.getElementById("lightConeIsFull").textContent === "O";
    const targets = [];
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    for (let i = 1; i <= targetCount; i++) {
        const character = document.getElementById(`char-${i}`);
        const lightCone = document.getElementById(`lightcone-${i}`);
        if (character && lightCone) { // Check if elements exist
            targets.push({
                character: character.value,
                lightCone: lightCone.value
            });
        }
    }

    const data = {
        sungok: sungok,
        ticket: ticket,
        targets: targets,
        characterCount: characterCount,
        characterIsFull: characterIsFull,
        lightConeCount: lightConeCount,
        lightConeIsFull: lightConeIsFull
    };

    fetch('/calculator/calculate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json()) // 서버에서 JSON 응답을 기대하는 경우
        .then(data => {
            console.log('성공:', data);
            displayResult(data.result, data.isPossible, data.diffSungok);
            // 서버 응답 처리
        })
        .catch((error) => {
            console.error('실패:', error);
            // 오류 처리
        });
}
function displayResult(result, isPossible, diffSungok) {
    const resultContainer = document.getElementById('result-container');
    const resultStatus = document.getElementById('result-status');
    const resultImage = document.getElementById('result-image'); // 이미지 요소 가져오기

    if (isPossible) {
        resultStatus.textContent = `기대값 대비 ${diffSungok}성옥 남습니다.`;
        resultStatus.className = 'result-status success';
        resultImage.src = 'images/possible.png'; // 이미지 경로 설정
        resultImage.alt = '가능'; // alt 텍스트 설정
    } else {
        resultStatus.innerHTML = `
    <p>기대값 대비 ${Math.abs(diffSungok)}성옥 부족합니다.</p>
    <p>초회 기준 ${Math.abs(diffSungok)} 성옥은 대략 ${price(Math.abs(diffSungok))}원 입니다.</p>
`;
        resultStatus.className = 'result-status warning';
        resultImage.src = 'images/impossible.png'; // 이미지 경로 설정
        resultImage.alt = '불가능'; // alt 텍스트 설정
    }

    resultContainer.style.display = 'block';
}

// 서버에서 응답을 받은 후 호출하는 예시
function handleCalculationResponse(response) {
    displayResult(response.result, response.isPossible, response.diffSungok);
}
function price(diffSungok) {
    const calculatedPrice = diffSungok * 19; // 입력값에 19를 곱합니다.

    // 숫자를 1,000 단위로 쉼표를 추가하는 함수
    function formatNumberWithCommas(number) {
        return number.toLocaleString();
    }

    const formattedPrice = formatNumberWithCommas(calculatedPrice); // 쉼표 추가

    return formattedPrice; // 포맷된 가격 반환
}