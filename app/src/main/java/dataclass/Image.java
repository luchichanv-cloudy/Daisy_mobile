package dataclass;

public class Image {
        private String mName;
        private String mImageUrl;

        public Image() {
            //empty constructor needed
        }

        public Image(String name, String imageUrl) {
            if (name.trim().equals("")) {
                name = "No Name";
            }

            mName = name;
            mImageUrl = imageUrl;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getImageUrl() {
            return mImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            mImageUrl = imageUrl;
        }
}
