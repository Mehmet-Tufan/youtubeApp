package com.mehmett.repository;

import com.mehmett.entity.User;
import com.mehmett.entity.Video;
import com.mehmett.utility.ConnectionProvider;
import com.mehmett.utility.ConsoleTextUtils;
import com.mehmett.utility.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoRepository implements ICrud<Video> {
	private final ConnectionProvider connectionProvider;
	private String sql;
	private static VideoRepository instance;
	
	private VideoRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	public static VideoRepository getInstance() {
		if (instance == null) {
			instance = new VideoRepository();
		}
		return instance;
	}
	
	@Override
	public Optional<Video> save(Video video) {
		
		sql = "INSERT INTO tblvideo (user_id,title,description,views) VALUES (?, ?, ?, ?)";
		
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, video.getUser_id());
			preparedStatement.setString(2, video.getTitle());
			preparedStatement.setString(3, video.getDescription());
			preparedStatement.setLong(4, video.getViews());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Video kaydedilirken hata oluştu.");
		}
		return Optional.ofNullable(video);
	}
	
	@Override
	public Optional<Video> update(Video video) {
		sql = "UPDATE tblvideo SET title=?,description=?,views=? WHERE id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, video.getTitle());
			preparedStatement.setString(2, video.getDescription());
			preparedStatement.setLong(3, video.getViews());
			preparedStatement.setLong(4, video.getId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Video güncellenirken hata oluştu.");
		}
		return Optional.ofNullable(video);
	}
	
	@Override
	public void delete(Long silinecekVideoId) {
		sql = "DELETE FROM tblvideo WHERE id = ?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, silinecekVideoId);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Video silinirken hata oluştu.");
		}
	}
	
	@Override
	public List<Video> findAll() {
		//(Long user_id, String title, String description, Long views)
		sql = "SELECT * FROM tblvideo";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("id");
				Long user_id = rs.getLong("user_id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				long views = rs.getLong("views");
				videoList.add(new Video(id, user_id, title, description, views));
			}
			
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Video listesi görüntülenirken hata oluştu.");
		}
		return videoList;
	}
	
	@Override
	public Optional<Video> findById(Long bulunacakVideoId) {
		sql = "SELECT * FROM tblvideo WHERE id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, bulunacakVideoId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				Long user_id = rs.getLong("user_id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				long views = rs.getLong("views");
				return Optional.of(new Video(bulunacakVideoId, user_id, title, description, views));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Aradığınız video bulunurken hata oluştu.");
		}
		return Optional.empty();
	}
	
	public List<Video> getTrendVideos() {
		sql = "SELECT * FROM tblvideo ORDER BY views DESC LIMIT 5";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("id");
				Long user_id = rs.getLong("user_id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				long views = rs.getLong("views");
				videoList.add(new Video(id, user_id, title, description, views));
			}
			
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Video listesi görüntülenirken hata oluştu.");
		}
		return videoList;
	}
	
	public List<Video> findVideosByTitle(String baslik) {
		sql = "SELECT * FROM tblvideo WHERE title ILIKE ?";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setString(1, "%" + baslik + "%");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("id");
				Long user_id = rs.getLong("user_id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				long views = rs.getLong("views");
				videoList.add(new Video(id, user_id, title, description, views));
			}
			
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Video listesi görüntülenirken hata oluştu.");
		}
		return videoList;
	}
	
	public List<Video> findVideosOfUser(User user) {
		sql = "SELECT * FROM tblvideo WHERE user_id=?";
		List<Video> videoList = new ArrayList<>();
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, user.getId());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("id");
				Long user_id = rs.getLong("user_id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				Long views = rs.getLong("views");
				videoList.add(new Video(id, user_id, title, description, views));
			}
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Aradığınız video bulunurken hata oluştu.");
		}
		return videoList;
	}
	
	public void goruntulenmeArttir(Video video) {
		sql = "UPDATE tblvideo SET views=views+1 WHERE id=?";
		try (PreparedStatement preparedStatement = connectionProvider.getPreparedStatement(sql)) {
			preparedStatement.setLong(1, video.getId());
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			ConsoleTextUtils.printErrorMessage("VideoRepository: Goruntulenme arttırılırken hata oluştu.");
		}
	}
}