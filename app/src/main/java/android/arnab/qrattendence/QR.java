package android.arnab.qrattendence;

import android.content.Context;

import com.stringcare.library.SC;
import com.tempos21.t21crypt.crypter.Crypter;
import com.tempos21.t21crypt.exception.CrypterException;
import com.tempos21.t21crypt.exception.DecrypterException;
import com.tempos21.t21crypt.exception.EncrypterException;
import com.tempos21.t21crypt.factory.CryptMethod;
import com.tempos21.t21crypt.factory.CrypterFactory;

public class QR
{
    Context mContext;
    String information,KEY_TOKEN;
    public QR(Context mContext, String KEY_TOKEN)
    {
        this.mContext=mContext;
        this.information=information;
        this.KEY_TOKEN=KEY_TOKEN;
    }

    public String getEncryptedString(String information) throws CrypterException, EncrypterException
    {
        final Crypter crypter=CrypterFactory.buildCrypter(CryptMethod.AES,KEY_TOKEN);
        information=crypter.encrypt(information);

        information=crypter.encrypt(information);

        information=crypter.encrypt(information);

        information=crypter.encrypt(information);


        return information;
    }

    public String getDecryptedString(String information) throws CrypterException, DecrypterException
    {
        final Crypter crypter = CrypterFactory.buildCrypter(CryptMethod.AES, KEY_TOKEN);

        information = crypter.decrypt(information);

        information = crypter.decrypt(information);

        information = crypter.decrypt(information);

        information = crypter.decrypt(information);

        return information;
    }
}
