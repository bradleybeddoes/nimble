<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">

<body>

<fb:login-button autologoutlink="true"></fb:login-button>
<div id="facebookcontinue">
  <a href="${createLink(action: "facebook")}">Use my facebook credentials</a>
  <fb:prompt-permission perms="email">Would you like to receive email from our application?</fb:prompt-permission>
</div>

<n:facebookConnect/>

</body>
</html>