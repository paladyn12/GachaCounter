# Gacha Counter
가챠 스택 저장 및 기대값 계산 서비스<br/>
개발 인원 : 1<br/>
개발 기간 : 25.01.01~25.01.19<br/>
# 설명
붕괴 : 스타 레일의 뽑기 횟수를 저장하여 기대값 계산, 시뮬레이션 등의 기능을 제공하는 웹 애플리케이션<br/>
# 주요 기능
 - 회원가입 및 로그인 : Google OAuth2 기반 로그인<br/>
 - 가챠 스택 저장 : 아이템 뽑기 횟수를 저장하여 확인 및 이하 기능에 적용<br/>
 - 가챠 시뮬레이터 : 미리 뽑기를 수행해볼 수 있는 시뮬레이션<br/>
 - 기대값 계산기 : 아이템을 얻을 기대 결과를 계산하여 반환<br/>
 - 화폐 수급처 모음 : 게임 내 화폐를 얻을 수 있는 경로를 모아 정리<br/>
# 개발 세부사항
 - 백엔드: Java Spring으로 개발되어 안정적이고 확장 가능한 서버 로직을 제공<br/>
 - 프론트엔드: JavaScript로 구현되어 동적이고 사용자 친화적인 인터페이스를 제공<br/>
 - 데이터베이스: MySQL을 사용하여 효율적인 데이터 저장 및 검색을 지원<br/>
 - 서버 호스팅: Docker로 컨테이너화하여 AWS EC2 인스턴스에 배포<br/>
 - 도메인: www.gachacounter.com 에서 접속 가능<br/>
# 배포 및 호스팅
 - 컨테이너화: Docker를 사용해 애플리케이션을 컨테이너화하여 이식성과 배포 용이성을 확보<br/>
 - 호스팅: AWS EC2의 Ubuntu 인스턴스에 서버 호스팅 및 배포<br/>
 - HTTPS: Let's Encrypt를 통해 암호화된 통신을 보장<br/>
# URL 링크
www.gachacounter.com<br/>
# 기술 스택
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/jpa-6DB33F?style=for-the-badge&logo=jpa&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/aws EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
