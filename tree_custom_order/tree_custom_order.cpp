/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   tree_custom_order.cpp                              :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: ShnurD6 <shnurd6@mail.ru>                  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2019/09/07 13:03:46 by ShnurD6           #+#    #+#             */
/*   Updated: 2019/09/07 17:12:42 by ShnurD6          ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#include "tree_custom_order.hpp"

struct	node
{
	node(
		size_t aId,
		size_t aLvl,
		std::vector<data_type>& aNodeWeights,
		std::vector<data_type>& aGlobalWeights)
	{
		this->Id = aId;
		this->Lvl = aLvl;
		this->Weight = 0;
		ft_assert(aNodeWeights.size() <= aGlobalWeights.size(), "Семён, блять, вектор ноды меньше вектора весов(");
		for (size_t i = 0; i < aNodeWeights.size(); i++)
		{
			Weight += aNodeWeights[i] * aGlobalWeights[i];
		}
	};

	size_t Lvl;
	size_t Weight;
	size_t Id;

	bool operator < (const node &point) const
	{ // это, возможно, нужно подрефакторить
   		if (this->Lvl == point.Lvl)
			return this->Weight > point.Weight;
		else
			return this->Lvl < point.Lvl;
	}
};

bool isStrNumeric(std::string& aStr)
{
	for (auto c: aStr)
	{
		if (c < '0' || c > '9')
			return false;
	}
	return true;
}

std::set<node> tree_custom_order(
	std::vector<std::vector<data_type>>& aNodes,
	std::vector<data_type>& aWeight,
	std::string& aOrder)
{
	auto cmp = [](data_type &x, data_type &y)
				{
					return x < y;
				};
	(void)cmp;
	std::set<node/*, decltype(cmp)*/> result;

	size_t lvl = 0;
	std::stringstream orderStream(aOrder);
	std::string iterator;
	size_t tmp_id;
	try
	{
		while (std::getline(orderStream, iterator, ';'))
		{
			if (iterator == "^")
				lvl--;
			else if (isStrNumeric(iterator))
			{
				tmp_id = std::stoi(iterator);
				result.insert(node(tmp_id, lvl, aNodes[tmp_id], aWeight));
				lvl++;
			}
			else
				throw std::invalid_argument(aOrder);
		}
	}
	catch(std::invalid_argument)
	{
		ft_assert(false, "Семён, у тебя гавно какое-то со строкой, разбериcь, пожалуйста\nТы скинул мне: \"" + aOrder + "\"");
	}

	return result;
}

void	test()
{
	std::vector<std::vector<data_type>> aNodes = {{1, 1, 2}, {2, 3 ,1}};
	std::vector<data_type> aHeight = {1, 1, 1};
	std::string aOrder = "0;^;1;";
	for (auto node: tree_custom_order(aNodes, aHeight, aOrder))
	{
		std::cout << "Id = " << node.Id << ", Вес = " << node.Weight << ", Lvl = " << node.Lvl << std::endl;
	}
}

std::vector<std::vector<data_type>> PyNodesToCpp(PyObject* aNodes)
{
	std::vector<std::vector<data_type>> result;
	ft_assert(PyList_Check(aNodes), "nodes is not a list, bleat, Semen!");
	for (Py_ssize_t i = 0; i < PyList_Size(aNodes); i++)
	{
		PyObject* node = PyList_GetItem(aNodes, i);
		result.push_back(std::vector<data_type>());
		for (Py_ssize_t j = 0; j < PyList_Size(node); j++)
		{
			PyObject *value = PyList_GetItem(node, j);
			result[i].push_back(PyFloat_AsDouble(value));
		}
	}
	return result;
}

std::vector<data_type> PyWeightToCpp(PyObject* aWeight)
{
	std::vector<data_type> result;
	ft_assert(PyList_Check(aWeight), "weight is not a list, bleat, Semen!");
	for (Py_ssize_t i = 0; i < PyList_Size(aWeight); i++)
	{
		PyObject* value = PyList_GetItem(aWeight, i);
		result.push_back(PyFloat_AsDouble(value));
	}
	return result;
}

PyObject* CppVectorToPy(const std::vector<data_type> &data)
{
	PyObject* listObj = PyList_New(data.size());
	ft_assert(listObj, "Can\'t allocate memoty to PyList");
	ft_assert(data.size() >= 1, "Answers is empty, somethings wrong");
	for (size_t i = 0; i < data.size(); i++)
	{
		PyObject *num = PyFloat_FromDouble(data[i]);
		assert(num && "Can\'t allocate memory to PyFloat");
		PyList_SET_ITEM(listObj, i, num);
	}
	return listObj;
}

PyObject* CppSetNodesToPy(const std::set<node> &data)
{
	PyObject* listObj = PyList_New(data.size());
	ft_assert(listObj, "Can\'t allocate memoty to PyList");
	ft_assert(data.size() >= 1, "Answers is empty, somethings wrong");
	
	size_t iter = 0;
	for (auto& node: data)
	{
		PyObject *num = PyFloat_FromDouble(node.Id);
		ft_assert(num, "Can\'t allocate memory to PyFloat");
		PyList_SET_ITEM(listObj, iter, num);
		iter++;
	}
	return listObj;
}

PyObject* tree_custom_order_with_convert(
	PyObject* aNodes,
	PyObject* aWeight,
	PyObject* aOrder)
{
	std::vector<std::vector<data_type>> Nodes = PyNodesToCpp(aNodes);
	std::vector<data_type> Weight = PyWeightToCpp(aWeight);
	std::string Order = PyBytes_AsString(aOrder);

	std::set<node> answer = tree_custom_order(Nodes, Weight, Order);

	PyObject* result = CppSetNodesToPy(answer);
	return result;
}

static PyObject *tco_wrapper(PyObject *self, PyObject *args)
{
	PyObject *Nodes = NULL; // vector<vector<int>>
	PyObject *Weight = NULL; // vector<int>
	PyObject *Order = NULL; // std::string

	// parse arguments
	PyArg_ParseTuple(args, "OOS", &Nodes, &Weight, &Order);

	return tree_custom_order_with_convert(Nodes, Weight, Order);
}

static PyMethodDef TcoMethods[] =
{
	{ "TreeOrdering", tco_wrapper, METH_VARARGS, "Order a tree. Input: 1) Nodes[[]] (PyList), 2) Weight[] (PyList), 3) Order (PyString)" },
	{ NULL, NULL, 0, NULL }
};

static struct PyModuleDef TcoModule = 
{
    PyModuleDef_HEAD_INIT,
    "TcoModule",
    "Tree custom order module",
    -1,
	TcoMethods
};

PyMODINIT_FUNC PyInit_TcoModule(void)
{
    return PyModule_Create(&TcoModule);
}
