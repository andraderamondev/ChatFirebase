package ramon.dev.chatfirebase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ramondev on 3/18/18.
 */

class MessageRvAdapter extends RecyclerView.Adapter<MessageRvAdapter.ViewHolder> {
    private ArrayList<Mensagem> lista = new ArrayList<>();

    @Override
    public MessageRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensagem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageRvAdapter.ViewHolder holder, int position) {
        holder.tvMsg.setText(lista.get(position).getMsg());
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public void atualizarLista(ArrayList<Mensagem> lista) {
        this.lista = lista;
        ordenarMensagens();
        notifyDataSetChanged();
    }

    public void addMsg(Mensagem value) {
        lista.add(value);
        ordenarMensagens();
        notifyDataSetChanged();
    }

    private void ordenarMensagens() {
        Collections.sort(lista, new Comparator<Mensagem>() {
            @Override
            public int compare(Mensagem o1, Mensagem o2) {
                return Integer.parseInt(o1.getId()) > Integer.parseInt(o2.getId()) ? -1 : Integer.parseInt(o1.getId()) < Integer.parseInt(o2.getId()) ? +1 : 0;
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMsg;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMsg = itemView.findViewById(R.id.tvMsg);
        }
    }
}
