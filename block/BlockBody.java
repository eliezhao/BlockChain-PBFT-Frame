package bc_demo.block;

import java.util.List;

/**
 * 区块body 里面放交易的数组
 *
 * @author ZhaoGao
 * @date 2020/7/22 - 19:27 - JavaProjects
 */
public class BlockBody {
    private List<ContentInfo> contentInfos;

    public List<ContentInfo> getContentInfos() {
        return contentInfos;
    }

    public void setContentInfos(List<ContentInfo> contentInfos) {
        this.contentInfos = contentInfos;
    }
}
