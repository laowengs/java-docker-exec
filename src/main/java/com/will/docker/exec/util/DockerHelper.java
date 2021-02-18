package com.will.docker.exec.util;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Image;

import java.util.List;

/**
 * docker操作类.
 * @author will
 */
public class DockerHelper {
    public static void execute(String ip,DockerAction dockerAction)throws Exception{
        DockerClient docker = DefaultDockerClient.builder().uri("http://".concat(ip).concat(":12375")).apiVersion("v1.30").build();
        dockerAction.action(docker);
        docker.close();
    }

    public static <T> T query(String ip,DockerQuery<T> dockerQuery)throws Exception{
        DockerClient docker = DefaultDockerClient.builder().uri("http://".concat(ip).concat(":12375")).apiVersion("v1.30").build();
        T result=dockerQuery.action(docker);
        docker.close();
        return result;
    }

    public interface DockerAction {
        void action(DockerClient docker) throws Exception;
    }

    public interface DockerQuery<T> {
        T action(DockerClient docker) throws Exception;
    }

    public static void main(String[] args) throws DockerException, InterruptedException {
        DockerClient docker = DefaultDockerClient.builder().uri("http://wengjp.local:12375").apiVersion("v1.30").build();
        List<Image> list = docker.listImages(DockerClient.ListImagesParam.allImages());
        for (Image image : list) {
            System.out.println(image);

        }
    }
}
