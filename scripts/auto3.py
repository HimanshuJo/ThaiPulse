import os

# Output directories
base_dir = "./generated_newsapp"
dirs = {
    "repository": os.path.join(base_dir, "repository"),
    "model": os.path.join(base_dir, "model"),
    "mapper": os.path.join(base_dir, "mapper"),
    "dto": os.path.join(base_dir, "dto")
}
for d in dirs.values():
    os.makedirs(d, exist_ok=True)

# Class names
base_names = [
    "ChiangMai",
    "KhonKaen",
    "HatYai",
    "NakhonRatchasima"
]

# Templates
repo_template = """package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.{MODEL_NAME};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface {REPO_NAME} extends JpaRepository<{MODEL_NAME}, Long> {{
    Page<{MODEL_NAME}> findAll(Pageable pageable);
    boolean existsByLink(String link);
}}
"""

model_template = """package com.thaipulse.newsapp.model;

import javax.persistence.*;

@Entity
@Table(name = "{TABLE_NAME}")
public class {MODEL_NAME} {{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String source;
    private String link;
    private String image;

    public {MODEL_NAME}() {{
    }}

    public {MODEL_NAME}(Long id, String title, String source, String link) {{
        this.id = id;
        this.title = title;
        this.source = source;
        this.link = link;
    }}

    public Long getId() {{
        return id;
    }}

    public void setId(Long id) {{
        this.id = id;
    }}

    public String getTitle() {{
        return title;
    }}

    public void setTitle(String title) {{
        this.title = title;
    }}

    public String getSource() {{
        return source;
    }}

    public void setSource(String source) {{
        this.source = source;
    }}

    public String getLink() {{
        return link;
    }}

    public void setLink(String link) {{
        this.link = link;
    }}

    public String getImage() {{
        return image;
    }}

    public void setImage(String image) {{
        this.image = image;
    }}
}}
"""

mapper_template = """package com.thaipulse.newsapp.mapper;

import com.thaipulse.newsapp.dto.{DTO_NAME};
import com.thaipulse.newsapp.model.{MODEL_NAME};

public class {MAPPER_NAME} {{

    public static {MODEL_NAME} toEntity({DTO_NAME} dto) {{
        {MODEL_NAME} news = new {MODEL_NAME}();
        news.setTitle(dto.getTitle());
        news.setLink(dto.getLink());
        news.setSource(dto.getSource());
        news.setImage(dto.getImage());
        return news;
    }}

    public static {DTO_NAME} toDto({MODEL_NAME} news) {{
        {DTO_NAME} dto = new {DTO_NAME}();
        dto.setTitle(news.getTitle());
        dto.setLink(news.getLink());
        dto.setSource(news.getSource());
        dto.setImage(news.getImage());
        return dto;
    }}
}}
"""

dto_template = """package com.thaipulse.newsapp.dto;

import lombok.Data;

@Data
public class {DTO_NAME} {{
    private String title;
    private String source;
    private String link;
    private String image;
}}
"""

# Generate files
for name in base_names:
    model_name = f"{name}News"
    repo_name = f"{name}NewsRepository"
    dto_name = f"{name}NewsDto"
    mapper_name = f"{name}NewsMapper"
    table_name = name.lower() + "_news"

    # Repository
    with open(os.path.join(dirs["repository"], f"{repo_name}.java"), "w", encoding="utf-8") as f:
        f.write(repo_template.format(MODEL_NAME=model_name, REPO_NAME=repo_name))

    # Model
    with open(os.path.join(dirs["model"], f"{model_name}.java"), "w", encoding="utf-8") as f:
        f.write(model_template.format(MODEL_NAME=model_name, TABLE_NAME=table_name))

    # Mapper
    with open(os.path.join(dirs["mapper"], f"{mapper_name}.java"), "w", encoding="utf-8") as f:
        f.write(mapper_template.format(MODEL_NAME=model_name, DTO_NAME=dto_name, MAPPER_NAME=mapper_name))

    # DTO
    with open(os.path.join(dirs["dto"], f"{dto_name}.java"), "w", encoding="utf-8") as f:
        f.write(dto_template.format(DTO_NAME=dto_name))

    print(f"Generated: {model_name}, {repo_name}, {dto_name}, {mapper_name}")

print("\nAll repositories, models, mappers, and DTOs generated successfully.")
