package gr.grnet.eseal.service;

import gr.grnet.eseal.enums.Sign;
import gr.grnet.eseal.exception.InternalServerErrorException;
import gr.grnet.eseal.sign.RemoteProviderCertificates;
import gr.grnet.eseal.sign.response.RemoteProviderCertificatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SignDocumentServiceFactory {

  private final RemoteSignDocumentServicePKCS7 remoteSignDocumentServicePKCS7;
  private final RemoteSignDocumentService remoteSignDocumentService;
  private final RemoteSignDocumentServicePKCS1 remoteSignDocumentServicePKCS1;

  @Autowired
  public SignDocumentServiceFactory(
      RemoteSignDocumentServicePKCS7 remoteSignDocumentServicePKCS7,
      RemoteSignDocumentService remoteSignDocumentService,
      RemoteSignDocumentServicePKCS1 remoteSignDocumentServicePKCS1) {
    this.remoteSignDocumentServicePKCS7 = remoteSignDocumentServicePKCS7;
    this.remoteSignDocumentService = remoteSignDocumentService;
    this.remoteSignDocumentServicePKCS1 = remoteSignDocumentServicePKCS1;
  }

  public SignDocumentService create(Sign sign) {

    switch (sign) {
      case REMOTE_SIGN:
        return remoteSignDocumentService;
      case PKCS7:
        return remoteSignDocumentServicePKCS7;
      case PKCS1:
        return remoteSignDocumentServicePKCS1;
      default:
        throw new InternalServerErrorException("Unable to sign the document");
    }
  }

  @Cacheable("remote_certificates")
  public RemoteProviderCertificatesResponse wrapGetUserCertificates(
      String username, String password, String endpoint, RemoteProviderCertificates rpc) {
    // need to wrap a static method in an instance method to make it use Spring's Cacheable
    // construct
    RemoteProviderCertificatesResponse userCertificates =
        SignDocumentService.getUserCertificates(username, password, endpoint, rpc);

    return userCertificates;
  }
}
