
<div class="socialaccount">

  <div class="logo">
    <n:socialimg name="${account.service.uid}" size="32"/> &nbsp;
  </div>

  <div class="details">
    <a href="${account.service.details.url.location}" alt="${account.service.details.url.altText}">
    <strong>${account.service.details?.name}</strong></a>
    &nbsp; | &nbsp;
    <a href="${account.profile.location}" alt="Service Profile" class="">
      My Profile
    </a>
    <br/>
    ${account.service.details?.description}
    <br/>

    
    <br/>

    
  </div>
</div>