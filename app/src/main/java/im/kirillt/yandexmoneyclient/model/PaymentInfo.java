package im.kirillt.yandexmoneyclient.model;


import android.content.res.Resources;

import org.parceler.Parcel;

import java.math.BigDecimal;

import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.model.util.BindableBoolean;
import im.kirillt.yandexmoneyclient.model.util.BindableString;

import static im.kirillt.yandexmoneyclient.utils.Converters.bigDecimalOrZero;
import static im.kirillt.yandexmoneyclient.utils.Converters.bigDecimalToAmountString;

@Parcel
public class PaymentInfo {
    private static final BigDecimal P2P_COMMISSION = new BigDecimal(0.005);

    public BindableString who = new BindableString();
    public BindableString whoError = new BindableString();

    public BindableString amountTotal = new BindableString();
    public BindableString amountToBePaid = new BindableString();
    public BindableString amountError = new BindableString();
    public BindableBoolean notEnoughMoney = new BindableBoolean();

    public BindableString message = new BindableString();
    public BindableBoolean codePro = new BindableBoolean();
    public BindableString codeproTime = new BindableString();

    public BindableBoolean refreshing = new BindableBoolean();
    public BindableString processResultMessage = new BindableString();
    public BindableString requestResultMessage = new BindableString();

    public BindableBoolean finished = new BindableBoolean();


    public Byte recursionFlag = 0;
    public BigDecimal balance;

    private Resources res;

    public PaymentInfo(BigDecimal balanceStr, String who, String amountTotal, String message,
                       boolean codePro, String codeproTime, Resources res) {
        this.balance = bigDecimalOrZero(balanceStr);
        this.who.set(who);
        this.amountTotal.set(amountTotal);
        this.message.set(message);
        this.codePro.set(codePro);
        if (codePro) {
            this.codeproTime.set(codeproTime);
        } else {
            this.codeproTime.set(null);
        }
        this.refreshing.set(false);
        this.finished.set(false);
        this.whoError.set(null);
        this.res = res;
    }

    public void changeAmountTotal() {
        if (recursionFlag == 2) {
            recursionFlag = 0;
            return;
        }
        recursionFlag = 1;
        BigDecimal tbp = BigDecimal.ZERO;
        try {
            tbp = new BigDecimal(amountToBePaid.get());
        } catch (Exception ignored) {
        }
        amountTotal.set(bigDecimalToAmountString(totalFromToBePaid(tbp)));
    }

    public void changeAmountTBP() {
        if (recursionFlag == 1) {
            recursionFlag = 0;
            return;
        }
        recursionFlag = 2;
        BigDecimal total = BigDecimal.ZERO;
        try {
            total = new BigDecimal(amountTotal.get());
        } catch (Exception ignored) {
        }
        amountToBePaid.set(bigDecimalToAmountString(toBePaidFromTotal(total)));
    }

    public boolean validateAmount() {
        notEnoughMoney.set(bigDecimalOrZero(amountToBePaid.get()).compareTo(balance) == 1);
        if (notEnoughMoney.get()) {
            amountError.set(res.getString(R.string.not_enough_money));
        } else {
            amountError.set(null);
        }
        return !notEnoughMoney.get();
    }


    public boolean validateWho() {
        /*int whoErrorRes = 0;
        String whoStr = who.get();
        if (whoStr.isEmpty()) {
            whoErrorRes = R.string.mandatory_field;
        } else {
            if (whoStr.contains("@") && !Validator.validatePhoneNumber(whoStr)) {
                whoErrorRes = R.string.invalid_email;
            } else {
                boolean vacc = Validator.validateAccountNumber(whoStr);
                boolean vphone = Validator.validatePhoneNumber(whoStr);

            }i
        }
        whoError.set(whoErrorRes != 0 ? res.getString(whoErrorRes) : null);
        return whoErrorRes != 0;*/
        return true;
    }


    private BigDecimal totalFromToBePaid(BigDecimal toBePaid) {
        BigDecimal rv = BigDecimal.ZERO;
        try {
            rv = toBePaid.divide(BigDecimal.ONE.add(P2P_COMMISSION), 2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return rv;
    }

    private BigDecimal toBePaidFromTotal(BigDecimal total) {
        BigDecimal rv = BigDecimal.ZERO;
        try {
            rv = total.add(total.multiply(P2P_COMMISSION));
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return rv;
    }
}
