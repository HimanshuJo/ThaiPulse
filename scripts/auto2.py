feeds = [
    "BicycleThailand",
    "ThaiCapitalist",
    "AboutThailandLiving",
    "DaveTheRavesThailand",
    "Thaifoodmaster",
    "ThailandBail",
    "TheSilomer",
    "PattayaPI",
    "BudgetCatcher",
    "ThaiLawyers",
    "MeanderingTales",
    "LifestyleInThailand",
    "ThatBangkokLife",
    "ThinglishLifestyle",
    "FashionGalleria"
]

def to_camel_case(name):
    return name[0].lower() + name[1:]

# Build sections
fields_section = []
constructor_section = []
methods_section = []

for feed in feeds:
    camel_name = to_camel_case(feed)
    service_name = f"{camel_name}RssFeedService"
    dto_name = f"{feed}NewsDto"

    # Service field
    fields_section.append(f"private final {feed}RssFeedService {service_name};")

    # Constructor assignment
    constructor_section.append(f"this.{service_name} = {service_name};")

    # Controller method
    endpoint = camel_name
    method_code = f"""
    @GetMapping(value = "/{endpoint}")
    public ResponseEntity<?> get{feed}News(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {{
        if (size < 1) {{
            size = 20;
        }}
        long totalNews = {service_name}.countAllNews();
        if (totalNews > 1000) {{
            Page<{dto_name}> pagedResult = {service_name}.getPaginatedNews(page, size);
            return ResponseEntity.ok().body(pagedResult);
        }} else {{
            List<{dto_name}> allNews = {service_name}.getPaginatedNews(0, (int) totalNews).getContent();
            return ResponseEntity.ok().body(allNews);
        }}
    }}
    """
    methods_section.append(method_code.strip())

# Combine into one controller file
controller_code = f"""
package com.example.news;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/news")
public class GeneratedNewsController {{

    // === Service Fields ===
    {'\n    '.join(fields_section)}

    // === Constructor ===
    public GeneratedNewsController(
        {',\n        '.join(f"{feed}RssFeedService {to_camel_case(feed)}RssFeedService" for feed in feeds)}
    ) {{
        // Rest add here!
        {'\n        '.join(constructor_section)}
    }}

    // === Controller Methods ===
    {'\n\n    '.join(methods_section)}
}}
"""

# Write to file
with open("GeneratedNewsController.java", "w", encoding="utf-8") as f:
    f.write(controller_code)

print("✅ Generated controller written to GeneratedNewsController.java")
