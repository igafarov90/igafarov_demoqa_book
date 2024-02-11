package api.models;

import java.util.List;
import lombok.Data;

@Data
public class UserBooksModel{
	private List books;
	private String userId;
	private String username;
}