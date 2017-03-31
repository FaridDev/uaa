package pl.java.scalatech.web;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.ProfileApp;

@RestController
@Slf4j
@RequestMapping("/oauth2")
@Profile(ProfileApp.PROFILE)
public class AuthHelpController {
	private final OAuth2RestOperations restTemplate;

	private final OAuth2ClientContext context;

	private final ClientDetailsService clientDetailsService;

	public AuthHelpController(OAuth2RestOperations restTemplate, OAuth2ClientContext context,
			ClientDetailsService clientDetailsService) {
		super();
		this.restTemplate = restTemplate;
		this.context = context;
		this.clientDetailsService = clientDetailsService;
	}

	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@GetMapping("/clientToken")
	OAuth2AccessToken clientToken() {
		return restTemplate.getAccessToken();
	}

	@GetMapping("/user2")
	public Object user2(Principal user) {
		OAuth2Authentication authentication = (OAuth2Authentication) user;
		Authentication userAuthentication = authentication.getUserAuthentication();
		return userAuthentication.getPrincipal();
	}

	@GetMapping("/info")
	String info() {
		OAuth2AccessToken token = context.getAccessToken();
		String tokenValueBeforeDeletion = token.getValue();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return "Oauth Token from context : " + tokenValueBeforeDeletion;
	}

	@GetMapping("/client/{clientId}")
	ClientDetails client(@PathVariable String clientId) {
		ClientDetails cd = clientDetailsService.loadClientByClientId(clientId);
		return cd;
	}

	@GetMapping("/details")
	Object details(OAuth2Authentication authentication) {
		return authentication.getUserAuthentication();
	}

	@GetMapping("/show_token")
	String authCode() throws Exception {
		OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
		String tokenValue = details.getTokenValue();
		return tokenValue;
	}

	@GetMapping(value = "userOauth", produces = "application/json")
	Map<String, Object> user(OAuth2Authentication user) {
		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("user", user.getUserAuthentication().getPrincipal());
		userDetails.put("authorities",
				AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
		return userDetails;
	}
}