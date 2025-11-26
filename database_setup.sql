-- Optional: Manually create the embedding column
-- This is NOT required - the system will create it automatically
-- But if you want to see it in your database right away, run this:

ALTER TABLE items 
ADD COLUMN IF NOT EXISTS description_embedding TEXT;

-- Verify it was created:
SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name = 'items' AND column_name = 'description_embedding';



