package tw.com.work.packaging.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import org.eclipse.jgit.util.FS;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GitUtil {
    public static void main(String[] args) {
        String localCodeDir = "D:\\workspace\\mirle\\package\\tmp\\a"; //本地文件夹
        String remoteRepoPath = "git@gitlab.mirle.com.tw:88a/88a-data-worker.git"; //git地址
        String keyPath = "C:\\Users\\stevenwu\\.ssh\\id_rsa";  //私钥文件
        gitClone(remoteRepoPath, localCodeDir, keyPath);
    }

    //localRepoPath 为本地文件夹
    //keyPath 私钥文件 path
    //remoteRepoPath 为 ssh git远端仓库地址
    protected static void gitClone(String remoteRepoPath, String localRepoPath, String keyPath) {
        //ssh session的工厂,用来创建密匙连接
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch sch = super.createDefaultJSch(fs);
                sch.addIdentity(keyPath); //添加私钥文件
                return sch;
            }
        };

        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();
        Git git = null;
        try {
//            PipedInputStream in = new PipedInputStream();
//            PipedOutputStream out = new PipedOutputStream(in);
            FileWriter file = new FileWriter("D:\\workspace\\mirle\\package\\tmp\\output.txt");
            git = cloneCommand.setURI(remoteRepoPath) //设置远程URI
                    .setTransportConfigCallback(transport -> {
                        SshTransport sshTransport = (SshTransport) transport;
                        sshTransport.setSshSessionFactory(sshSessionFactory);
                    })
                    .setDirectory(new File(localRepoPath)) //设置下载存放路径
//                    .setProgressMonitor(new TextProgressMonitor(new PrintWriter(out)))
                    .setProgressMonitor(new TextProgressMonitor(new PrintWriter(file)))
//                    .setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
                    .call();
//            try (in) {
//                String inContent = new String(in.readAllBytes());
//                System.out.println(inContent);
//            }
            System.out.println("success");
        } catch (Exception e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            if (git != null) {
                git.close();
            }
        }
    }
}
