package app.api.json.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.api.json.databinding.ItemsFragmentBinding
import app.api.json.model.Item
import app.api.json.model.RemoteItemsRepository
import app.api.json.viewmodel.ItemsViewModel
import app.api.json.viewmodel.ItemsViewModelFactory

class ItemsFragment : Fragment() {

    private val itemsViewModel: ItemsViewModel by viewModels {
        ItemsViewModelFactory(RemoteItemsRepository())
    }
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemsAdapter = ItemsAdapter(requireContext(), arrayListOf(), ItemListener())
        return ItemsFragmentBinding.inflate(inflater).apply {
            initializeRecyclerView(this)
        }.root
    }

    private fun initializeRecyclerView(binding: ItemsFragmentBinding) {
        binding.recyclerView.adapter = itemsAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemsViewModel.items.observe(viewLifecycleOwner) { items ->
            itemsAdapter.setItems(items)
        }

        itemsViewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.swipeRefresh.isRefreshing = loading
        }
        binding.swipeRefresh.setOnRefreshListener {
            itemsViewModel.fetchItems()
        }
    }

    private inner class ItemListener: ItemsAdapter.Listener {
        override fun onItemClick(item: Item) {
            val bottomSheetFragment = BottomSheetWebViewFragment(item.url)
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
    }
}