package com.contactapp.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.contactapp.R;
import com.contactapp.roomDb.Contacts;
import com.contactapp.roomDb.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactsViewHolder>  implements Filterable {

    private Context mCtx;
    private ArrayList<Contacts> contactList;
    private ArrayList<Contacts> contactListTemp;

    public ContactAdapter(Context mCtx, ArrayList<Contacts> contactList) {
        this.mCtx = mCtx;
        this.contactList = contactList;
        contactListTemp = new ArrayList<>(contactList);
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_contact, parent, false);
        return new ContactsViewHolder(view);
    }
    private AlertDialog AskOption(final Context context, final int adapterPosition) {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete contact from Phonebook")

                .setPositiveButton("Delete Contact", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        DatabaseClient.getInstance(context).getAppDatabase()
                                .contactDao()
                                .delete(contactList.get(adapterPosition));
                        notifyDataSetChanged();
                        context.startActivity(new Intent(context,MainActivity.class));
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {
        final Contacts t = contactList.get(position);
        holder.email.setText(t.getEmail());
        holder.username.setText(String.format("%s %s", t.getFirst_name(), t.getLast_name()));
        holder.address.setText(t.getAddress());
        holder.phone.setText(t.getPhone_number());
        holder.mainView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AskOption(holder.itemView.getContext(),holder.getAdapterPosition()).show();
                return false;
            }
        });
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(),UpdateContact.class);
                intent.putExtra("contact",t);
            holder.itemView.getContext().startActivity(intent);
            }
        });


    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contacts> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactListTemp);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contacts item : contactListTemp) {
                    if (item.getFirst_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView username, email, address, phone;
        RelativeLayout mainView;

        public ContactsViewHolder(final View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.tvUserName);
            email = itemView.findViewById(R.id.tvEmail);
            address = itemView.findViewById(R.id.tvAddress);
            phone = itemView.findViewById(R.id.tvPhone);
            mainView = itemView.findViewById(R.id.rlContactList);



        }



        @Override
        public void onClick(View view) {
            Contacts contacts = contactList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateContact.class);
            intent.putExtra("contacts", contacts);

            mCtx.startActivity(intent);
        }
    }
}