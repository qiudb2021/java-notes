package cn.itcast.nio.c1;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFilesWalkFileTree {
    public static void main(String[] args) throws  IOException {

    }

    /*
    遍历目录及文件
     */
    public void m1() {
        final AtomicInteger dirCount = new AtomicInteger();
        final AtomicInteger fileCount = new AtomicInteger();

        try {
            Files.walkFileTree(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_321"), new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    System.out.println("===============>" + dir);
                    dirCount.incrementAndGet();
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("    " + file);
                    fileCount.incrementAndGet();
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("dir count "+ dirCount);
        System.out.println("file count " + fileCount);
    }

    // 统计jar文件个数
    public void m2() throws IOException {
        final AtomicInteger jarCount = new AtomicInteger();

        Files.walkFileTree(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_321"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".jar")) {
                    System.out.println(file);
                    jarCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });

        System.out.println("jar count: " + jarCount);
    }

    // 删除多级目录
    public void m3() throws IOException {
        Files.walkFileTree(Paths.get(""), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("======> 进入目录: " + dir);
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // 删除目录下的文件
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("<======= 退出目录： " + dir);
                // 退出目录后，目录一定为空
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }
}
