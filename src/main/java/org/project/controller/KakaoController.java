/*
 * package org.project.controller;
 * 
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
 * 
 * import org.project.service.UserService; import
 * org.springframework.context.annotation.Description; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.ResponseBody;
 * 
 * import lombok.RequiredArgsConstructor; import lombok.extern.log4j.Log4j;
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @Controller
 * 
 * @RequiredArgsConstructor
 * 
 * @Log4j public class KakaoController { private final KakaoTokenJsonData
 * kakaoTokenJsonData; private final KakaoUserInfo kakaoUserInfo;
 * 
 * private final UserService uservice;
 * 
 * @GetMapping("/index") public String index() { return "loginForm"; }
 * 
 * @Description("ȸ���� �Ҽ� �α����� ��ġ�� �ڵ����� ����Ǵ� API�Դϴ�. �ΰ� �ڵ带 �̿��� ��ū�� �ް�, �ش� ��ū���� ����� ������ ��ȸ�մϴ�."
 * + "����� ������ �̿��Ͽ� ���񽺿� ȸ�������մϴ�.")
 * 
 * @GetMapping("/oauth")
 * 
 * @ResponseBody public String kakaoOauth(@RequestParam("code") String code) {
 * log.info("�ΰ� �ڵ带 �̿��Ͽ� ��ū�� �޽��ϴ�."); KakaoTokenResponse kakaoTokenResponse =
 * kakaoTokenJsonData.getToken(code);
 * log.info("��ū�� ���� �����Դϴ�.{}",kakaoTokenResponse); KakaoUserInfoResponse
 * userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
 * log.info("ȸ�� ���� �Դϴ�.{}",userInfo);
 * 
 * userService.createUser(userInfo.getKakao_account().getEmail());
 * 
 * return "okay"; } }
 * 
 */