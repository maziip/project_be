# BE-001A 리뷰 (Reviewer Agent, 2026-09-10)

대상: PR #2 `BE-001A: GitHub Actions CI 워크플로 추가` (commit `751b504`, `40f38d3`, `e19d858`, `6e097f2`, `7cf61b9`)
기준: 커리큘럼 8.7 코드 리뷰 프롬프트. 코드를 대신 고치지 않고 문제의 이유와 재현 방법을 제시한다.

## 1. 요구사항 충족

- 충족. `.github/workflows/ci.yml`이 main 대상 PR과 main push에서 `./gradlew build`(컴파일+테스트)를 실행한다. Ruleset `main 보호`가 `build` 검사 통과와 PR을 병합 조건으로 강제한다.
- 증거: 실행 3회. 성공(`v4`) → 성공(`v5`, 경고 0) → 고의 실패 commit `e19d858`에서 `AppTest > appHasAGreeting() FAILED`, `1 test completed, 1 failed`로 실패 → revert `6e097f2`에서 성공. `GET /rules/branches/main` 결과 규칙 4개 적용.

## 2. 버그와 데이터 손실 위험

- 해당 없음. 워크플로는 읽기 전용 작업(checkout, JDK 설치, 빌드)만 수행한다.

## 3. 트랜잭션·동시성 / 4. 외부 API 실패 처리

- 해당 없음.

## 5. 테스트 누락

- 없음. `build` 태스크가 `test`를 포함하므로 테스트 실패가 곧 CI 실패다. 고의 실패 실험으로 확인됨.
- **[Question]** 같은 브랜치에 push가 연달아 오면 이전 실행이 끝나기 전에 새 실행이 시작되어 둘 다 돈다. 지금은 문제 없지만 실행 시간이 길어지면 `concurrency`로 이전 실행을 취소하는 설정이 있다. 필요해질 때 찾아볼 키워드로만 기록.

## 6. 보안과 민감정보

- 확인 결과 문제 없음. 저장소의 기본 워크플로 토큰 권한이 `read`로 설정되어 있음(`GET /actions/permissions/workflow` = `read`). 비밀정보 사용 없음.
- **[Minor]** 파일 안에 `permissions:` 선언이 없어 저장소 설정에 의존한다. 파일 최상위에 `permissions: { contents: read }`를 명시하면 저장소 설정이 바뀌어도 이 워크플로의 권한은 그대로이고, 파일만 봐도 권한을 알 수 있다. 작성자 판단에 맡긴다.
  - 재현(확인 방법): 추가 후 push하면 실행 로그 "Set up job" 단계에 `GITHUB_TOKEN Permissions: Contents: read`가 표시된다.
- 액션 버전을 `@v6`, `@v5` 메이저 태그로 참조한다. GitHub 공식 액션이므로 지금은 허용. 서드파티 액션을 추가할 때는 commit 해시 고정을 고려할 것.

## 7. 가독성과 책임 분리

- **[Minor]** 세 스텝 모두 `name:`이 없어 실행 로그에 `Run actions/setup-java@v5`, `Run ./gradlew build`처럼 표시된다. 지금은 세 개뿐이라 읽힌다. 스텝이 늘어나면 `name: Build with Gradle` 같은 이름을 붙일 것.
- **[Minor]** `pull_request:` 블록 뒤에 공백 2칸만 있는 빈 줄이 있다(8행). 동작에는 영향 없음. 편집기에서 trailing whitespace 표시를 켜 두면 잡힌다.

## 8. 불필요한 복잡성

- 없음. 최소 구성이 맞다.
- **[Question]** 캐시가 없어 매 실행마다 gradle 9.7.1 zip과 의존성을 새로 받는다(빌드 단계 28~40초). 지금은 무시해도 된다. 빌드가 1분을 넘기기 시작하면 `gradle/actions/setup-gradle`을 검토할 것. 문서 예시에서 이 스텝을 뺀 이유(최소 구성으로 먼저 초록불)를 PR 설명 ②에 적어 두면 나중에 판단 근거가 된다.

## PR 설명에 대한 의견

- **[Major]** ①~③에 리뷰어가 검증할 수 있는 정보가 없다. BE-001 PR 설명은 3차 수정 끝에 파일 경로, 명령, 출력 인용이 들어갔는데 이번 설명은 그 수준에 못 미친다. 최소한 다음이 들어가야 한다.
  - ①: 파일 경로, 실행 이벤트 두 가지, 스텝 세 개, Ruleset 이름과 규칙 두 개.
  - ②: "claude의 안내에 따라 실행"은 설계 이유가 아니다. 이벤트를 PR+main push로 고른 이유는 세션에서 작성자가 직접 말했다("PR은 목표 하나가 끝난 시점, main 병합은 다른 작업과 섞인 시점"). 그 문장을 쓸 것. gradle을 설치하지 않는 이유(wrapper), setup-gradle을 뺀 이유도 여기.
  - ③: 실행 3회의 결과와 로그 인용. 고의 실패 commit과 revert commit 해시. 규칙 적용 확인 결과.
- **[Question]** ④의 두 항목("시간적 여유", "이 방식이 맞는지")은 PR이 아니라 학습 운영에 대한 질문이다. PR 설명에서 빼고 일일 기록이나 LEARNING_STATE에 옮길 것. 질문 자체는 튜터가 별도로 답한다. ④에는 이 PR의 한계를 적는다. 후보: 승인자 0명 규칙, 캐시 없음, 한국어 GitHub Docs가 `setup-java@v4`로 구버전이었던 것.

## 사소한 것

- PR 설명 ③의 "CI.yml"은 실제 파일명 `ci.yml`과 대소문자가 다르다. 리눅스 러너에서는 대소문자를 구분하므로 문서에 적을 때도 실제 이름을 쓸 것.

## 반영 요청 요약

| 심각도 | 항목 | 작성자 결정 |
|---|---|---|
| Major | PR 설명 ①~③에 경로·이유·증거 보강 | |
| Question | ④의 학습 운영 질문을 일일 기록으로 이동, PR 한계로 교체 | |
| Minor | `permissions: contents: read` 명시 여부 | |
| Minor | 스텝 `name:` 추가 여부, 8행 공백 | |
