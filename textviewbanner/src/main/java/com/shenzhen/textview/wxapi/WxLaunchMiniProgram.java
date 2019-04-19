package com.shenzhen.textview.wxapi;

import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.d;


public class WxLaunchMiniProgram {
    public WxLaunchMiniProgram() {
    }

    public static final class Resp extends BaseResp {
        public String extMsg;

        public Resp() {
        }

        public Resp(Bundle var1) {
            this.fromBundle(var1);
        }

        public final void fromBundle(Bundle var1) {
            super.fromBundle(var1);
            this.extMsg = var1.getString("_launch_wxminiprogram_ext_msg");
        }

        public final void toBundle(Bundle var1) {
            super.toBundle(var1);
            var1.putString("_launch_wxminiprogram_ext_msg", this.extMsg);
            var1.putInt("_wxapi_command_type", this.getType());
        }

        public final int getType() {
            return 19;
        }

        public final boolean checkArgs() {
            return true;
        }
    }

    public static final class Req extends BaseReq {
        private static final String TAG = "MicroMsg.SDK.WXLaunchMiniProgram.Req";
        public static final int MINIPTOGRAM_TYPE_RELEASE = 0;
        public static final int MINIPROGRAM_TYPE_TEST = 1;
        public static final int MINIPROGRAM_TYPE_PREVIEW = 2;
        public String userName;
        public String path = "";
        public int miniprogramType = 0;

        public Req() {
        }

        public final int getType() {
            return 19;
        }

        public final boolean checkArgs() {
            if (d.b(this.userName)) {
                Log.e("MicroMsg.SDK.WXLaunchMiniProgram.Req", "userName is null");
                return false;
            } else if (this.miniprogramType >= 0 && this.miniprogramType <= 2) {
                return true;
            } else {
                Log.e("MicroMsg.SDK.WXLaunchMiniProgram.Req", "miniprogram type should between MINIPTOGRAM_TYPE_RELEASE and MINIPROGRAM_TYPE_PREVIEW");
                return false;
            }
        }

        public final void toBundle(Bundle var1) {
            super.toBundle(var1);
            var1.putString("_launch_wxminiprogram_username", this.userName);
            var1.putString("_launch_wxminiprogram_path", this.path);
            var1.putInt("_launch_wxminiprogram_type", this.miniprogramType);
        }
    }
}
