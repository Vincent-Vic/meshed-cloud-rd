package cn.meshed.cloud.rd.domain.repo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * <h1>存储文件信息</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Data
public class RepositoryFile {

    /**
     * 新文件路径(含文件名)
     */
    @Setter(AccessLevel.NONE)
    private String filePath;

    /**
     * 旧文件路径(含文件名)
     */
    private String oldFilePath;

    /**
     * 文件内容
     */
    private String content;

    public RepositoryFile() {
    }

    public RepositoryFile(String filePath, String content) {
        this.filePath = filePath;
        this.oldFilePath = filePath;
        this.content = content;
    }

    public RepositoryFile(String filePath, String oldFilePath, String content) {
        this.filePath = filePath;
        this.oldFilePath = oldFilePath;
        this.content = content;
    }

    /**
     * 默认让旧文件名称和新新建名称相同，如果先设置旧文件名称者不会生效
     *
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
        if (StringUtils.isEmpty(this.oldFilePath)) {
            this.oldFilePath = filePath;
        }
    }
}
