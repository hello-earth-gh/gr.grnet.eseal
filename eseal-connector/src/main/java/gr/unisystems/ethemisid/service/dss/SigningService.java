/*
 * Copyright (C) 2020 Unisystems.gr
 * All rights Reserved
 */
package gr.unisystems.ethemisid.service.dss;

import gr.unisystems.ethemisid.service.dss.exception.DssException;

import java.io.IOException;

public interface SigningService { 
    public void config(String url, int connectionTimeout, int socketTimeout);
    public byte[] sign(byte[] bytes, byte[] imageBytes, String fileName, String username, String password, String key, boolean imageVisibility) throws DssException, IOException;
}