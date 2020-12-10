/*
 * Copyright 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Map;

/**
 * This is a factory class which finds the appropriate Writer subclass for the BarcodeFormat
 * requested and encodes the barcode with the supplied contents.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class MultiFormatWriter implements Writer {

    @Override
    public BitMatrix encode(String contents,
                            int width,
                            int height, ErrorCorrectionLevel level) throws WriterException {
        return encode(contents, width, height, null, level);
    }

    @Override
    public BitMatrix encode(String contents,
                            int width, int height,
                            Map<EncodeHintType, ?> hints, ErrorCorrectionLevel level) throws WriterException {

        Writer writer = new QRCodeWriter();
        return writer.encode(contents, width, height, hints, level);
    }
}
