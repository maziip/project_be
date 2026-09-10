# Learning State

- 마지막 갱신: 2026-09-10 (세션 종료, 3일차. PR #1 병합 완료. BE-001A CI·병합 보호 완료, PR #2는 설명 보강 전이라 미병합)
- 현재 주차: 1주 차 (3일차 종료)
- 진행 중 티켓: BE-001A (PR #2 열림, 리뷰 반영·병합 남음), 다음 BE-002
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

- 없음. PR 설명 `docs/pr/BE-001.md` 25행 오타 "21f로" → "21로" (사소, 내일 수정)

## AI 도움 기록

- 도움을 받은 부분: 환경 점검 명령 실행, 공식 문서 기반 버전 조사, 운영 파일 템플릿 작성 (Tutor Agent, 2026-09-08)
- 도움을 받은 부분: Compose 작성 시 YAML 맵/리스트 구분, 최상위 `volumes:` 문법(두 줄 예시 제공), 오류 메시지 위치 해석. 파일 자체는 학습자가 작성 (2026-09-08)
- AI 없이 다시 설명하거나 구현 가능한지: 버전 선택 이유(Java 21 vs 25, MySQL 9.7)는 학습자가 VERSIONS.md를 보고 설명할 수 있어야 함. 다음 세션에서 확인 질문 예정
- Docker 자기점검 결과 (2026-09-08, AI 없이 답변): 이미지/컨테이너 차이 ○, `down` 후 남는 것 ○(컨테이너 "종료"→"삭제"로 보정), 호스트/컨테이너 포트 ○, 재기동 명령 △(`docker start mysql`→`docker compose up -d`로 보정), `.env` 전달 경로 ✕(설명 제공: .env → compose 치환 → 컨테이너 환경변수 → entrypoint 첫 초기화). 다음 세션 재확인: 3번과 5번
- 2026-09-09 BE-001: 튜터가 제공한 것 — Gradle/wrapper/DSL 개념(C# 비유), `gradle init` 선택지 표, `git status --short` 읽는 법, 최상위 파일 역할 정답, mainClass 오류 원인 지목(수정은 학습자), Guava 제거 위치, 패키지 변경 명령. 학습자가 직접 한 것 — 모든 명령 실행, 파일 편집, 테스트 깨뜨리기·복구, PR 설명 작성(2회 재작성), 리뷰 반영 2건
- 2026-09-09 Docker 자기점검 재확인: 3번(.env 전달 경로)·5번(재기동) 모두 본인 말로 정답. 통과
- 2026-09-09 `gradle init` 파일 역할 추측: settings(△→.sln), build.gradle.kts(○), wrapper.properties(△→ProjectVersion.txt), libs.versions.toml(○), gradlew(△→wrapper 스크립트)
- 2026-09-09 Java 문법 자가 설명: package≈namespace ○, main/println ○, import≈using ○, import static≈using static(설명 후 이해), @Test≈[Test] 어트리뷰트(설명 후 이해)
- 2026-09-09 겪은 일: `--scan` 실행으로 빌드 메타데이터가 gradle.com에 1회 업로드됨(약관 동의). 샘플 코드라 민감정보 없음. 이후 미사용 결정. PR 설명 1차본에 튜터 안내문을 그대로 붙여넣음 → 본인 문장으로 재작성 요구 → 2차·3차 수정으로 해결
- 학습자 현재 상태: Gradle, Spring 모두 처음. 1주 차는 C#/Unity 비유(build.gradle≈.csproj, wrapper≈Unity Hub 버전 고정, Spring≈ASP.NET Core)로 설명 시작

## 평가 결과와 보충 과제

- 평가 Agent: 없음 (아직 평가 전)
- 점수와 근거: -
- 보충할 항목: -

## 커리큘럼 변경 제안

- 없음

## BE-001 계획 (학습자 동의, 2026-09-08)

- 티켓: Gradle 애플리케이션 골격과 wrapper를 구성하고 명령행에서 빌드·테스트한다
- 접근 방법: `brew install gradle`(1회) 후 `gradle init`으로 Java application 골격 생성, wrapper는 VERSIONS.md의 9.7.1로 고정. Spring은 넣지 않는다(2주 차)
- 완료 증거: `./gradlew build` 출력의 `BUILD SUCCESSFUL`, `./gradlew test` 테스트 1개 이상 통과 출력, 두 출력을 이 파일에 기록. 브랜치 `BE-001`에서 작업 후 PR 설명 작성
- 시작 전 확인할 것: `gradle init`이 만드는 파일 각각의 역할을 학습자가 설명할 수 있는지

## BE-001 진행 기록 (2026-09-09)

- 브랜치 `BE-001`, PR #1 https://github.com/maziip/project_be/pull/1
- 환경: `brew install gradle`이 JDK 26을 동반 설치 → `JAVA_HOME`을 Temurin 21로 고정(~/.zshrc). toolchain(21) 설정으로 컴파일 JDK는 환경과 무관하게 21 고정 확인(튜터 실험: JAVA_HOME=26으로 빌드 → class major version 65)
- `gradle init` 선택: Application / Java / 21 / project_be / Single / Kotlin DSL / JUnit Jupiter / new APIs no. wrapper 9.7.1, toolchain 21 자동 생성 확인
- 증거: `./gradlew build` BUILD SUCCESSFUL(7 tasks), 테스트 결과 XML `tests="1" failures="0"`, `./gradlew run` → `Hello World!`. 테스트 고의 실패 실험: `BUILD FAILED`, `AssertionFailedError at AppTest.java:12`, HTML 리포트 확인 후 원복
- 리뷰(docs/reviews/BE-001-review.md) 반영: 미사용 Guava 제거(commit 8180c60), 패키지 `org.example`→`knowledgeassistant`(같은 commit), PR 설명 보완(commit 1928900). 미반영: 없음. App.java 샘플은 BE-002에서 삭제 예정
- commit: 3502290(골격), 8180c60(리뷰 반영), 1928900(PR 설명)

## BE-001A 진행 기록 (2026-09-10)

- 브랜치 `BE-001A`, PR #2 https://github.com/maziip/project_be/pull/2
- 시작 전 질문: CI 정의 → "코드 검증 시스템"(자동·매번 두 요소 보강 필요). 실행 이벤트 → "PR 열 때 + main 합칠 때", 이유 정확. wrapper가 있어 러너에 gradle 설치 불필요 → 모름, 설명 후 CI 로그 `Downloading .../gradle-9.7.1-bin.zip`로 확인
- 파일: `.github/workflows/ci.yml`. `on: push/pull_request` 모두 `branches: ["main"]`. 잡 `build`, ubuntu-latest, 스텝 checkout@v6 → setup-java@v5(temurin 21) → `./gradlew build`. 뼈대는 튜터 제공, steps는 GitHub Docs 조각을 보고 학습자 작성
- 증거: 1차 성공 35s(setup-java@v4, deprecated 경고 2건) → v5로 갱신 후 2차 성공 39s 경고 0 → 고의 실패 commit `e19d858` CI 실패(`AppTest > appHasAGreeting() FAILED`, `1 test completed, 1 failed`) → `git revert` commit `6e097f2` CI 성공
- 병합 보호: Ruleset `main 보호`(id 22769159) Active, 대상 `~DEFAULT_BRANCH`, 규칙 pull_request(승인 0) + required_status_checks(`build`) + deletion + non_fast_forward. 1차 생성 시 대상 브랜치 누락(적용 규칙 0) → 수정 후 4개 적용 확인
- 겪은 일: commit 없이 push 2회(아침 docs, 오후 ci.yml → "No commits between main and BE-001A"). add/commit/push를 "고르기/사진 찍기/보내기"로 정리. 한국어 GitHub Docs가 setup-java@v4로 구버전 안내 → 실제 실행 경고로 발견
- 튜터 반성: "CI"를 첫날 결정만 기록하고 설명 안 함 → 오늘 처음부터 설명. 존재하지 않는 파일 경로를 링크로 제시해 혼란. 설명 밀도가 높다는 피드백 받고 1라운드/2라운드로 분할
- PR 설명 1차본 작성(commit `7cf61b9`). Reviewer 리뷰 `docs/reviews/BE-001A-review.md` 작성(Major 1: ①~③ 증거 부족, Question 1: ④가 학습 운영 질문). 학습자가 "내용을 아직 제대로 이해하지 못했다"는 이유로 오늘 반영을 중단하기로 결정. PR #2는 열어 둔 채 종료. 미병합
- 학습자 질문(④에서): "시간적 여유가 얼마나 있는지", "이 방식으로 학습하는 게 맞는지". 튜터 답: 표준 일과 21:00 이후엔 새 단계 착수 대신 마무리를 우선 제안(금지 아님, 결정은 학습자). 방식 순서는 커리큘럼 고정, 설명 밀도·분량은 조정 가능. 질문의 의도(느림/어려움/효용 의심)를 내일 시작 시 한 줄로 확인

## 운영 규칙 추가 (2026-09-10)

- 일일 기록의 "가설/실제 원인"은 오류가 아닌 개념 이해 문제일 때 "해당 없음 + 이유"로 쓴다. 빈칸으로 두지 않는다
- "AI 답변을 검증한 방법"은 "무엇을 어떻게 돌려서 무엇을 봤다" 형태의 구체 행동 하나로 쓴다
- PR 병합은 일반 merge. LEARNING_STATE와 리뷰 기록이 commit 해시를 증거로 가리키므로 squash하지 않는다

## 다음 행동

1. (완료) gh CLI 설치, 로그인, 첫 commit push.
2. (완료) Docker Desktop 설치와 결과 기록.
3. (완료) MySQL 9.7 Docker Compose 작성·기동·연결 확인. `docker/docker-compose.yml` 커밋은 학습자가 수행.
4. (완료) LLM API: Claude / 월 $20 / 80% Mock 전환. 계정·키 발급·한도 설정은 5주 차 직전(4주 차 금요일)에 수행.
5. (완료) Docker 자기점검 재확인, BE-001 골격·리뷰 반영·PR 설명.
6. (완료) 일일 기록 빈칸, PR 설명 오타, PR #1 일반 merge, BE-001A CI 파일·실행 3회·Ruleset.
7. 다음 세션(2026-09-11) 순서:
   1. 학습자에게 ④ 질문의 의도 한 줄 확인. 어제 이해 못 했다고 한 부분이 무엇인지 확인(후보: 워크플로 파일 각 줄, PR 이벤트가 다시 도는 이유, Ruleset과 CI의 관계, wrapper)
   2. 그 부분을 짧게 다시 설명하고 학습자가 본인 말로 되말하기
   3. `docs/pr/BE-001A.md` ①~④를 리뷰대로 보강(본인 문장). `docs/reviews/BE-001A-review.md`와 함께 commit·push
   4. CI 초록 확인 후 PR #2 일반 merge. 로컬 main 동기화
   5. `docs/notes/2026-09-10.md` 빈칸 채우기
   6. BE-002 착수 전 커리큘럼 1주 차 BE-002 조건 확인. 21:00 이후 새 단계 착수 제안 안 함
   7. 보조자료 `docs/notes/1주차-도구-개념-정리.html/.pdf`(2026-09-10 밤 작성, 학습자 요청 "따라 하긴 했는데 익히지 못했다"). 끝의 확인 질문 10개를 학습자가 답한 뒤 튜터가 확인. 필수 항목: git add/commit/push 루프, 티켓 한 바퀴(gh), gradlew build 결과 읽기, YAML 규칙 4개