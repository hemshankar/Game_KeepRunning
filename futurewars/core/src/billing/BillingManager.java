package billing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.pay.Information;
import com.badlogic.gdx.pay.Offer;
import com.badlogic.gdx.pay.OfferType;
import com.badlogic.gdx.pay.PurchaseManager;
import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseManagerTestSupport;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.PurchaseSystem;
import com.badlogic.gdx.pay.Transaction;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by hsahu on 12/16/2017.
 */

public class BillingManager {

    public PurchaseManagerConfig purchaseManagerConfig;
    public PlatformResolver resolver = null;
    public BillingManager(){
        init();
    }
    public void init(){
        // Disposes static instances in case JVM is re-used on restarts
        PurchaseSystem.onAppRestarted();

        //set config
        purchaseManagerConfig = new PurchaseManagerConfig();
        purchaseManagerConfig.addOffer(new Offer().setType(OfferType.CONSUMABLE).setIdentifier("coins_1000"));

        resolver = new AndroidResolver();

    }


    public PurchaseObserver purchaseObserver = new PurchaseObserver() {

        @Override
        public void handleRestore (Transaction[] transactions) {
            for (int i = 0; i < transactions.length; i++) {
                if (checkTransaction(transactions[i].getIdentifier()) == true) break;
            }
        }

        @Override
        public void handleRestoreError (Throwable e) {
            // getPlatformResolver().showToast("PurchaseObserver: handleRestoreError!");
            Gdx.app.log("ERROR", "PurchaseObserver: handleRestoreError!: " + e.getMessage());
            throw new GdxRuntimeException(e);
        }

        @Override
        public void handleInstall () {
            // getPlatformResolver().showToast("PurchaseObserver: installed successfully...");
            Gdx.app.log("handleInstall: ", "successfully..");
        }

        @Override
        public void handleInstallError (Throwable e) {
            // getPlatformResolver().showToast("PurchaseObserver: handleInstallError!");
            Gdx.app.log("ERROR", "PurchaseObserver: handleInstallError!: " + e.getMessage());
            throw new GdxRuntimeException(e);
        }

        @Override
        public void handlePurchase (Transaction transaction) {
            checkTransaction(transaction.getIdentifier());
        }

        @Override
        public void handlePurchaseError (Throwable e) {
            if (e.getMessage().equals("There has been a Problem with your Internet connection. Please try again later")) {

                // this check is needed because user-cancel is a handlePurchaseError too)
                // getPlatformResolver().showToast("handlePurchaseError: " + e.getMessage());
            }
            throw new GdxRuntimeException(e);
        }

        @Override
        public void handlePurchaseCanceled () {
        }
    };

    protected boolean checkTransaction (String ID) {
        boolean returnbool = false;
        if ("coins_1000".equals(ID)) {
            System.out.println("purchase successfull");
            returnbool = true;
        }
        return returnbool;
    }


    public class AndroidResolver extends PlatformResolver {

        private final static String GOOGLEKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgfVTHlFXcjClDnTUh3iz1HBIiDaPJeQx2xWNoCB0kpfsQRjdaFVcBQS2i6fhliZi9MYLMZ/+QQ5gt0s/RHvD1ukibGyBLf6V2SDGtA6XcpQIwP4XPGuctxy2IuOGXrSnZBDMOdHQoE+C6WTsJVft/3DA6Pp4aSQBicvAo/s9gozc032l6d9D9PF6vuUqhP/zrC3/a79QccEGKCqJkGfAFYaF9WwpDgi5pFBnl7JrctopAj+HDfnbHUad7xiR49W/NEq3zN4uMGB+BJzydZw2OQ91wfA7AmMu5BOpemYUlc+KegNn45tgkhFIunZdd/3UlZvgm9zWyEpTGzGAtXMv9QIDAQAB";
        static final int RC_REQUEST = 10001;	// (arbitrary) request code for the purchase flow

        public AndroidResolver() {

            PurchaseManagerConfig config = purchaseManagerConfig;
            config.addStoreParam(PurchaseManagerConfig.STORE_NAME_ANDROID_GOOGLE, GOOGLEKEY);

            initializeIAP(null, purchaseObserver, config);
        }
    }

    public abstract class PlatformResolver {

        protected PurchaseManager mgr;

        public PlatformResolver () { 	}

        public void initializeIAP (PurchaseManager mgr, PurchaseObserver purchaseObserver, PurchaseManagerConfig config) {

            this.mgr = mgr;

            // set and install the manager manually
            if (mgr != null) {
                PurchaseSystem.setManager(mgr);
                PurchaseSystem.install(purchaseObserver, config);
            } else {
                Gdx.app.log("", "gdx-pay: initializeIAP(): purchaseManager == null => call PurchaseSystem.hasManager()");
                if (PurchaseSystem.hasManager()) { // install and get the manager automatically via reflection
                    this.mgr = PurchaseSystem.getManager();
                    PurchaseSystem.install(purchaseObserver, config); // install the observer
                    Gdx.app.log("", "gdx-pay: installed manager: " + this.mgr.toString());
                }
            }
        }

        public void restorePurchase(){
            if (mgr != null) {
                PurchaseSystem.purchaseRestore();
                Gdx.app.log("gdx-pay", "PurchaseSystem.purchase");
            } else {
                Gdx.app.log("ERROR", "gdx-pay: requestPurchase(): purchaseManager == null");
            }
        }

        public void requestPurchase (String productString) {
            // if (PurchaseSystem.hasManager()) {
            if (mgr != null) {
                PurchaseSystem.purchase(productString);
                Gdx.app.log("gdx-pay", "PurchaseSystem.purchase");
            } else {
                Gdx.app.log("ERROR", "gdx-pay: requestPurchase(): purchaseManager == null");
            }
        }

        public void requestPurchaseRestore () {
            // if (PurchaseSystem.hasManager()) {
            if (mgr != null) {
                PurchaseSystem.purchaseRestore();
            } else {
                Gdx.app.log("ERROR", "gdx-pay: requestPurchaseRestore(): purchaseManager == null");
            }
        }

        public PurchaseManager getPurchaseManager () {
            return mgr;
        }

        public boolean cancleTestPurchase() {
            if (mgr instanceof PurchaseManagerTestSupport) {
                ((PurchaseManagerTestSupport) mgr).cancelTestPurchases();
                return true;
            }
            return false;
        }

        public Information getProductInformation(String prodId){
            Information information = PurchaseSystem.getInformation(prodId);
            return information;
        }
    }
}
