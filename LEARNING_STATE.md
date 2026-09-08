# Learning State

- 마지막 갱신: 2026-09-08
- 현재 주차: 시작 전 준비 (0주차)
- 진행 중 티켓: 없음 (준비 완료 후 BE-001 시작)
- 현재 월별 관문 상태: 미진행

## 시작 전 준비 점검표

| 항목 | 상태 | 증거 |
|---|---|---|
| JDK 설치 | 완료 | 아래 "완료한 항목" 참고 |
| Git 설치 | 완료 | `git version 2.50.1 (Apple Git-155)` |
| Docker 설치 | 미완료 | `docker: command not found` (2026-09-08) |
| IDE | 부분 | VS Code만 설치됨. IntelliJ IDEA CE는 선택 |
| VERSIONS.md 버전 고정 | 완료 | `VERSIONS.md` (2026-09-08) |
| OpenSearch-Java client 호환성·벡터 검색 확인 | 부분 | client 3.x ↔ 서버 3.x 호환 확인. k-NN 플러그인 포함 여부는 컨테이너 기동 후 확인 |
| 학습 저장소 생성 | 완료 | `/Users/dreadwitch/Documents/project_be`, origin `https://github.com/maziip/project_be.git` |
| LEARNING_STATE.md 생성 | 완료 | 이 파일 |
| MySQL Docker Compose 실행 확인 | 미완료 | Docker 설치 후 진행 |
| .gitignore 및 환경변수 구성 | 완료(초안) | `.gitignore`, `.env.example` (값 없음). 실제 `.env`는 커밋 금지 |
| 원격 저장소·CI 결정 | 완료 | GitHub + GitHub Actions (BE-001A에서 구성) |
| 외부 LLM API 계정·예산·중단 기준 | 미결정 | 5주 차 전까지 결정. 후보: Anthropic Claude API. 예산 상한과 중단 기준은 학습자가 정한다 |

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
- AI 없이 다시 설명하거나 구현 가능한지: 버전 선택 이유(Java 21 vs 25, MySQL 9.7)는 학습자가 VERSIONS.md를 보고 설명할 수 있어야 함. 다음 세션에서 확인 질문 예정

## 평가 결과와 보충 과제

- 평가 Agent: 없음 (아직 평가 전)
- 점수와 근거: -
- 보충할 항목: -

## 커리큘럼 변경 제안

- 없음

## 다음 행동

1. (완료) gh CLI 설치, 로그인, 첫 commit push.
2. Docker Desktop을 설치하고 `docker version`, `docker compose version` 결과를 이 파일에 기록한다.
3. MySQL 9.7 Docker Compose를 작성해 컨테이너를 기동하고 연결을 확인한다 (학습자가 먼저 작성 시도).
4. 외부 LLM API 제공자·월 예산·호출 중단 기준을 결정해 이 파일에 기록한다.
5. 준비 완료 조건을 모두 충족하면 BE-001(Gradle 골격과 wrapper)로 진입한다.
