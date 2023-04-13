package algonquin.cst2335.cst2335_final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ArticleAdapter class is a custom RecyclerView.Adapter to display a list of articles.
 * It handles the creation of view holders and binds article data to the views.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    /**
     *  List of articles to be displayed
     */
    private List<Article> articles;
    /**
     * Listener for handling article click events
     */
    private OnArticleClickListener onArticleClickListener;

    /**
     * Constructor for the ArticleAdapter.
     * Initializes the articles list and sets the click listener.
     */
    public ArticleAdapter(OnArticleClickListener onArticleClickListener) {
        this.articles = new ArrayList<>();
        this.onArticleClickListener = onArticleClickListener;
    }

    /**
     * Other methods of the ArticleAdapter class (onCreateViewHolder, onBindViewHolder, getItemCount, etc.)
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(itemView, onArticleClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.headline.setText(article.getHeadline());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public List<Object> getArticles() {
        return Collections.singletonList(articles);
    }

    /**
     * ArticleViewHolder class is a custom RecyclerView.ViewHolder for displaying articles.
     * It contains a TextView for the article headline and a click listener.
     */
    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView headline;
        private OnArticleClickListener onArticleClickListener;

        /**
         *  Constructor for the ArticleViewHolder.
         *  Initializes the UI elements and sets the click listener.
         * @param itemView
         * @param onArticleClickListener
         */
        public ArticleViewHolder(@NonNull View itemView, OnArticleClickListener onArticleClickListener) {
            super(itemView);
            headline = itemView.findViewById(R.id.headline);
            this.onArticleClickListener = onArticleClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onArticleClickListener.onArticleClick(getAdapterPosition());
        }
    }

    /**
     * OnArticleClickListener is an interface for handling article click events.
     */
    public interface OnArticleClickListener {
        /**
         * Called when an article is clicked in the RecyclerView.
         * @param position The position of the clicked article in the RecyclerView
         */
        void onArticleClick(int position);
    }
}

