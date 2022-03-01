/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.unisystems.ethemisid.service.dss.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class SigningImages {

   private List<byte[]> images = new ArrayList<byte[]>();
   public static final int GEETDD_LOGO_INDEX = 0;
   public static final int STE_LOGO_INDEX = 1;
   public static SigningImages _instance = null;
   
   public SigningImages() {
      loadImages();
   }
   
   public static final SigningImages getInstance() {
      if (_instance == null) {
         _instance = new SigningImages();
      }
      
      return _instance;
   }
   
   private void loadImages() {
      byte[] bytes;
      
      try (final InputStream inputStream = this.getClass().getResourceAsStream("/sign_logo_GEETDD.png")) {
         bytes = IOUtils.toByteArray(inputStream);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      images.add(bytes);

      try (final InputStream inputStream = this.getClass().getResourceAsStream("/sign_logo_ste_2008_lite.jpg")) {
         bytes = IOUtils.toByteArray(inputStream);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      images.add(bytes);
   }
   
   public List<byte[]> getImages() {
      return images;
   }
}
