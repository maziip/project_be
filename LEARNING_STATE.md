# Learning State

- 마지막 갱신: 2026-09-08 (준비 단계 종료)
- 현재 주차: 시작 전 준비 완료 → 1주 차 진입 대기
- 진행 중 티켓: 없음 (다음 세션 BE-001 시작)
- 현재 월별 관문 상태: 미진행

## 시작 전 준비 점검표

| 항목 | 상태 | 증거 |
|---|---|---|
| JDK 설치 | 완료 | 아래 "완료한 항목" 참고 |
| Git 설치 | 완료 | `git version 2.50.1 (Apple Git-155)` |
| Docker 설치 | 완료 | Docker Desktop 4.90.0, Engine 29.7.2, Compose v5.5.1, `hello-world` 성공 (2026-09-08) |
| IDE | 부분 | VS Code만 설치됨. IntelliJ IDEA CE는 선택 |
| VERSIONS.md 버전 고정 | 완료 | `VERSIONS.md` (2026-09-08) |
| OpenSearch-Java client 호환성·벡터 검색 확인 | 부분 | client 3.x ↔ 서버 3.x 호환 확인. k-NN 플러그인 포함 여부는 컨테이너 기동 후 확인 |
| 학습 저장소 생성 | 완료 | `/Users/dreadwitch/Documents/project_be`, origin `https://github.com/maziip/project_be.git` |
| LEARNING_STATE.md 생성 | 완료 | 이 파일 |
| MySQL Docker Compose 실행 확인 | 완료 | `docker/docker-compose.yml`. `docker compose ps` Up, 로그 `ready for connections. Version: '9.7.2' port: 3306`, 호스트 `nc -z localhost 3306` 성공, `SELECT VERSION()` = 9.7.2 (2026-09-08) |
| .gitignore 및 환경변수 구성 | 완료 | `.gitignore`, `docker/.env.example`. `git check-ignore`로 `docker/.env` 제외 확인. compose는 `${VAR}`로만 참조 |
| 원격 저장소·CI 결정 | 완료 | GitHub + GitHub Actions (BE-001A에서 구성) |
| 외부 LLM API 계정·예산·중단 기준 | 결정 완료 (계정은 5주 차 직전 생성) | 제공자: Anthropic Claude API. 월 예산 $20 (선결제 크레딧 $20, 자동 충전 끔, 콘솔 월 지출 한도 $20). 중단 기준: 누적 $16(80%) 도달 시 그달은 Mock Provider만 사용. 사용량 확인: 콘솔 Usage 페이지, 위치는 계정 생성 시 기록. 개발 기본 모델은 저가 등급(Haiku 4.5, $1/$5 per MTok) 사용. 가격 확인일 2026-09-08 |

## 완료한 항목과 증거

- 항목: JDK 21 설치
  - commit 또는 파일: (환경 설정, 파일 없음)
  - 실행 명령: `brew install --cask temurin@21` 후 `java -version && javac -version && /usr/libexec/java_home -V`
  - 결과:
    ```
    openjdk version "21.0.12.1" 2026-08-18 LTS
    OpenJDK Runtime Environment Temurin-21.0.12.1+1 (build 21.0.12.1+1-LTS)
    OpenJDK 64-Bit Server VM Temurin-21.0.12.1+1 (build 21.0.12.1+1-LTS, mixed mode, sharing)
    javac 21.0.12.1
    21.0.12.1 (arm64) "Eclipse Adoptium" /Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home
    ```
- 항목: Docker Desktop 설치
  - 실행 명령: `brew install --cask docker-desktop` 후 `docker version && docker compose version && docker run --rm hello-world`
  - 결과: `Server: Docker Desktop 4.90.0 (238679)`, Engine `29.7.2`, `Docker Compose version v5.5.1`, `Hello from Docker!`
- 항목: MySQL 9.7 Docker Compose (학습자 직접 작성)
  - commit 또는 파일: `docker/docker-compose.yml`, 볼륨 `docker_mysql_data`
  - 결정: 태그 `mysql:9.7`(패치 자동 반영), 비밀번호는 `docker/.env` + `${VAR}` 참조, 호스트 포트 3306(`lsof -i :3306` 비어 있음 확인), named volume(초기 SQL 없음)
  - 실행 명령: `cd docker && docker compose config && docker compose up -d && docker compose ps`, `docker compose logs mysql | grep "ready for connections"`, `docker compose exec mysql mysql -uroot -p -e "SELECT VERSION();"`
  - 결과: `docker-mysql-1 mysql:9.7 Up ... 0.0.0.0:3306->3306/tcp`, `ready for connections. Version: '9.7.2' ... port: 3306`, `VERSION() = 9.7.2`
  - 겪은 오류와 원인: `no configuration file` (파일 미생성) → `empty compose file` → `services.ports must be a mapping` (서비스 이름 단계 누락) → `volumes must be a mapping` (최상위 volumes를 리스트로 작성) → `services.mysql.volumes must be a array` (엉뚱한 volumes 수정) → `services.volumes additional properties` (최상위 volumes 들여쓰기) → 통과
- 항목: Git 확인
  - 실행 명령: `git --version`
  - 결과: `git version 2.50.1 (Apple Git-155)`
- 항목: 저장소 생성과 운영 파일 생성
  - commit 또는 파일: 첫 commit (`VERSIONS.md`, `LEARNING_STATE.md`, 커리큘럼 사본, `.gitignore`, `.env.example`)
  - 결과: 로컬 commit 완료. 학습자가 `gh auth login`(HTTPS, 계정 maziip) 후 push. `git log origin/main` = `f462315` 확인 (2026-09-08)

## 실패한 테스트와 미해결 결함

- 증상: 없음 (코드 없음)
- 현재 가설: -
- 다음 확인: -

## AI 도움 기록

- 도움을 받은 부분: 환경 점검 명령 실행, 공식 문서 기반 버전 조사, 운영 파일 템플릿 작성 (Tutor Agent, 2026-09-08)
- 도움을 받은 부분: Compose 작성 시 YAML 맵/리스트 구분, 최상위 `volumes:` 문법(두 줄 예시 제공), 오류 메시지 위치 해석. 파일 자체는 학습자가 작성 (2026-09-08)
- AI 없이 다시 설명하거나 구현 가능한지: 버전 선택 이유(Java 21 vs 25, MySQL 9.7)는 학습자가 VERSIONS.md를 보고 설명할 수 있어야 함. 다음 세션에서 확인 질문 예정
- 확인 질문(다음 세션): named volume과 bind mount의 차이, `down`과 `down -v`의 차이, compose에서 `.env`를 읽는 위치, 최상위 `volumes:`가 맵인 이유

## 평가 결과와 보충 과제

- 평가 Agent: 없음 (아직 평가 전)
- 점수와 근거: -
- 보충할 항목: -

## 커리큘럼 변경 제안

- 없음

## 다음 행동

1. (완료) gh CLI 설치, 로그인, 첫 commit push.
2. (완료) Docker Desktop 설치와 결과 기록.
3. (완료) MySQL 9.7 Docker Compose 작성·기동·연결 확인. `docker/docker-compose.yml` 커밋은 학습자가 수행.
4. (완료) LLM API: Claude / 월 $20 / 80% Mock 전환. 계정·키 발급·한도 설정은 5주 차 직전(4주 차 금요일)에 수행.
5. 시작 전 준비 완료. 다음 세션부터 1주 차 BE-001(Gradle 골격과 wrapper) 진입. 시작 전 학습자의 접근 방법과 테스트 계획을 먼저 질문한다.
