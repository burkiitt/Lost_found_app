# AI Search Feature - Setup Guide

## Overview
This AI-based search system uses semantic embeddings to find items by meaning, not just keywords. It can understand synonyms, typos, and related concepts.

## Features
- **Semantic Text Search**: Finds items based on meaning, not just exact keywords
- **Automatic Fallback**: Falls back to keyword search if AI search fails
- **Feature Flag**: Can be easily enabled/disabled
- **Background Processing**: Embeddings generated asynchronously to avoid blocking

## Setup Instructions

### 1. Enable/Disable Feature
Edit `src/main/resources/application.properties`:
```properties
# Set to false to disable AI search
ai.search.enabled=true
```

### 2. API Configuration (Optional)
The system uses Hugging Face Inference API (free tier). You can optionally add an API key:
```properties
ai.embedding.api.key=your_api_key_here
```

**Note**: The API works without a key for limited requests. For production, consider getting a free API key from [Hugging Face](https://huggingface.co/settings/tokens).

### 3. Database Setup
The system automatically creates the `description_embedding` column in the `items` table when first used. No manual database changes needed!

### 4. Generate Embeddings for Existing Items
Run the utility class to generate embeddings for all existing items:
```bash
# Run from your IDE or command line
java ru.relex.jakartaee_project.utils.EmbeddingGenerator
```

**Note**: This may take a while depending on the number of items. The API has rate limits on the free tier.

## How It Works

1. **When creating items**: Embeddings are automatically generated in the background
2. **When searching**: 
   - If AI search is enabled and embeddings exist, it uses semantic search
   - Falls back to keyword search if embeddings are unavailable or search fails
3. **Similarity threshold**: Items with similarity > 0.3 are returned (configurable in code)

## Disabling the Feature

To completely disable AI search:
1. Set `ai.search.enabled=false` in `application.properties`
2. The system will use only keyword search (original behavior)

## Troubleshooting

### Search not working with AI?
- Check if `ai.search.enabled=true` in properties
- Verify API is accessible (check internet connection)
- Check server logs for embedding generation errors
- System automatically falls back to keyword search

### Slow search?
- Embeddings are generated asynchronously for new items
- First search might be slower if embeddings need to be generated
- Consider running EmbeddingGenerator for existing items

### API Rate Limits?
- Free tier has rate limits
- Add a small delay between requests (already implemented)
- Consider getting an API key for higher limits

## Files Modified/Created

### New Files:
- `EmbeddingService.java` - Handles embedding generation
- `EmbeddingDao.java` - Database operations for embeddings
- `EmbeddingGenerator.java` - Utility to generate embeddings for existing items

### Modified Files:
- `ItemService.java` - Added semantic search method
- `ItemDao.java` - Added findByIds method
- `pom.xml` - Added HTTP client and JSON dependencies
- `application.properties` - Added AI search configuration

## Reverting Changes

If you need to undo these changes:
1. Set `ai.search.enabled=false` in `application.properties` (quick disable)
2. Or use Git to revert:
   ```bash
   git checkout HEAD -- src/main/java/ru/relex/jakartaee_project/service/ItemService.java
   git checkout HEAD -- src/main/java/ru/relex/jakartaee_project/dao/ItemDao.java
   git checkout HEAD -- pom.xml
   ```
3. Delete the new files if needed

## Future Enhancements

Possible improvements:
- Image-based search using CLIP model
- Local embedding model (no API dependency)
- Vector database integration (pgvector)
- Search result ranking improvements



